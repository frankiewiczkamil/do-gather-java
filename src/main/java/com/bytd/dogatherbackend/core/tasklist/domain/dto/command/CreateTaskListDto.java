package com.bytd.dogatherbackend.core.tasklist.domain.dto.command;

import com.bytd.dogatherbackend.core.tasklist.domain.Task;
import java.util.List;
import java.util.UUID;

public record CreateTaskListDto(
    UUID id, String name, String description, UUID creatorId, List<Task> tasks) {
  public CreateTaskListDto withId() {
    if (id != null) {
      return this;
    } else {
      return new CreateTaskListDto(UUID.randomUUID(), name, description, creatorId, tasks);
    }
  }

  public CreateTaskListDto withTasks() {
    List<Task> taskz = tasks == null ? List.of() : tasks.stream().map(Task::withId).toList();
    return new CreateTaskListDto(id, name, description, creatorId, taskz);
  }
}
