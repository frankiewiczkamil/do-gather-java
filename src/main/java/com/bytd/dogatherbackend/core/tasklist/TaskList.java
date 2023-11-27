package com.bytd.dogatherbackend.core.tasklist;

import com.bytd.dogatherbackend.core.tasklist.infra.db.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.infra.db.TaskListDbDto;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
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

  void addParticipant(UUID participantId, List<Role> roles) {
    if (participants.stream().anyMatch(p -> p.participantId().equals(participantId))) {
      throw new IllegalArgumentException("Participant already exists in the list");
    } else {
      participants.add(new Participant(participantId, roles));
    }
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

enum Role {
  OWNER,
  EDITOR,
  GUEST
}
