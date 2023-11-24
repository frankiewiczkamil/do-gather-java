package com.bytd.dogatherbackend.core.tasklist;

import java.util.UUID;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Task {
  private UUID id;
  private TaskState state;
  private short progressTotal;
  private int timeSpent;

  private String name;
  private String description;
  private UUID taskListId;

  public static Task create(CreateTaskDto dto, UUID taskListId) {
    var instance = new Task();
    instance.id = dto.id();
    instance.name = dto.name();
    instance.description = dto.description();
    instance.taskListId = taskListId;
    instance.state = TaskState.NEW;
    instance.progressTotal = 0;
    instance.timeSpent = 0;
    return instance;
  }

  boolean isIdConflict(UUID id) {
    return this.id.equals(id);
  }
}

enum TaskState {
  NEW,
  IN_PROGRESS,
  DONE,

  CANCELLED,
  REOPENED
}
