package com.bytd.dogatherbackend.core.tasklist;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

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
}

enum Role {
  OWNER,
  EDITOR,
  GUEST
}

record Participant(UUID participantId, List<Role> roles) {}
