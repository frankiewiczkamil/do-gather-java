package com.bytd.dogatherbackend.core.tasklist;

import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.AuthorIsNotAParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.GuestNotAllowedToAddAnotherParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantAlreadyAdded;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantRoleTooLowToAddAnotherParticipant;
import com.bytd.dogatherbackend.core.tasklist.infra.db.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.infra.db.TaskListDbDto;
import java.util.*;
import java.util.function.Supplier;

class TaskList {

  private UUID id;
  private String name;
  private String description;
  private List<Participant> participants;
  private List<Task> tasks;
  private UUID creatorId;

  static TaskList create(CreateTaskListDto dto) {
    var instance = new TaskList();
    instance.id = dto.id();
    instance.name = dto.name();
    instance.description = dto.description();
    instance.creatorId = dto.creatorId();
    instance.participants = new LinkedList<>();
    var roles = new LinkedList<Role>();
    roles.add(Role.OWNER);
    instance.participants.add(new Participant(dto.creatorId(), roles));
    instance.tasks = new LinkedList<>();

    return instance;
  }

  void addTask(CreateTaskDto taskDto) {
    if (tasks.stream().anyMatch(t -> t.isIdConflict(taskDto.id()))) {
      throw new IllegalArgumentException("Task already exists in the list");
    } else {
      Task task = Task.create(taskDto, id);
      tasks.add(task);
    }
  }

  void addParticipant(AddParticipantDto dto) {
    var author = getParticipant(dto.authorId());
    if (author.isEmpty()) {
      throw new AuthorIsNotAParticipant(dto.authorId());
    } else if (participants.stream().anyMatch(p -> p.participantId().equals(dto.participantId()))) {
      throw new ParticipantAlreadyAdded(dto.participantId());
    } else if (author.get().roles().stream().noneMatch(Role::isEditorOrOwner)) {
      throw new GuestNotAllowedToAddAnotherParticipant(dto.authorId());
    } else if (isRoleToBeAddedHigherThanAuthorRole(dto)) {
      throw new ParticipantRoleTooLowToAddAnotherParticipant(getRolesString(author.get()));
    } else {
      participants.add(new Participant(dto.participantId(), dto.roles()));
    }
  }

  private boolean isRoleToBeAddedHigherThanAuthorRole(AddParticipantDto dto) {
    var maybeAuthor = getParticipant(dto.authorId());
    if (maybeAuthor.isEmpty()) {
      throw new AuthorIsNotAParticipant(dto.authorId());
    }
    var authorRoles = maybeAuthor.get().roles();
    var authorHighestRole = Role.findHighestRole(authorRoles);
    var roleToBeAddedHighestRole = Role.findHighestRole(dto.roles());
    return roleToBeAddedHighestRole.compareTo(authorHighestRole) > 0;
  }

  private Optional<Participant> getParticipant(UUID participantId) {
    return participants.stream().filter(p -> p.participantId().equals(participantId)).findFirst();
  }

  private String getRolesString(Participant p) {
    return p.roles().stream().toList().toString();
  }

  TaskListDbDto toDbDto(
      Supplier<TaskListDbDto> listDbDtoSupplier, Supplier<TaskDbDto> taskDbDtoSupplier) {
    var dto = listDbDtoSupplier.get();
    dto.setId(id);
    dto.setName(name);
    dto.setDescription(description);
    dto.setCreatorId(creatorId);
    dto.setParticipants(participants);
    dto.setTasks(tasks.stream().map(task -> task.toDbDto(taskDbDtoSupplier)).toList());
    return dto;
  }
}

enum Role implements Comparable<Role> {
  GUEST,
  EDITOR,
  OWNER;
  private static final Comparator<Role> comparator = new RoleComparator();

  boolean isEditorOrOwner() {
    return this.equals(EDITOR) || this.equals(OWNER);
  }

  static Role findHighestRole(List<Role> roles) {
    var max = roles.stream().max(comparator);
    return max.orElseThrow(() -> new IllegalStateException("Roles list is empty"));
  }

  static class RoleComparator implements Comparator<Role> {
    @Override
    public int compare(Role o1, Role o2) {
      return o1.compareTo(o2);
    }
  }
}
