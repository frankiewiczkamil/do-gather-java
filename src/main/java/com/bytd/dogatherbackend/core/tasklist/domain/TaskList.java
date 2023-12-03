package com.bytd.dogatherbackend.core.tasklist.domain;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.AddParticipantDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskListDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Participant;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.AuthorIsNotAParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.GuestNotAllowedToAddAnotherParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantAlreadyAdded;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantRoleTooLowToAddAnotherParticipant;
import java.util.*;
import java.util.function.Supplier;

public class TaskList<TaskListDto extends TaskListDbDto, TaskDto extends TaskDbDto> {

  private UUID id;
  private String name;
  private String description;
  private List<Participant> participants;
  private List<Task> tasks;
  private UUID creatorId;

  public static <TaskListDto extends TaskListDbDto, TaskDto extends TaskDbDto>
      TaskList<TaskListDto, TaskDto> create(CreateTaskListDto dto) {
    var instance = new TaskList<TaskListDto, TaskDto>();
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

  public void addTask(CreateTaskDto taskDto) {
    if (tasks.stream().anyMatch(t -> t.isIdConflict(taskDto.id()))) {
      throw new IllegalArgumentException("Task already exists in the list");
    } else {
      Task task = Task.create(taskDto);
      tasks = new LinkedList<>(tasks);
      tasks.add(task);
    }
  }

  public void addParticipant(AddParticipantDto dto) {
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

  public TaskListDto toDbDto(
      Supplier<TaskListDto> listDbDtoSupplier, Supplier<TaskDto> taskDbDtoSupplier) {
    var dto = listDbDtoSupplier.get();
    dto.setId(id);
    dto.setName(name);
    dto.setDescription(description);
    dto.setCreatorId(creatorId);
    dto.setParticipants(participants);
    dto.setTasks(tasks.stream().map(task -> task.toDbDto(() -> taskDbDtoSupplier.get())).toList());
    return dto;
  }

  public static <TaskListDto extends TaskListDbDto, TaskDto extends TaskDbDto>
      TaskList<TaskListDto, TaskDto> fromDbDto(TaskListDto dto) {
    var instance = new TaskList<TaskListDto, TaskDto>();
    instance.id = dto.getId();
    instance.name = dto.getName();
    instance.description = dto.getDescription();
    instance.creatorId = dto.getCreatorId();
    instance.participants = dto.getParticipants();
    instance.tasks = dto.getTasks().stream().map(Task::fromDbDto).toList();
    return instance;
  }
}
