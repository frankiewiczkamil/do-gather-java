package com.bytd.dogatherbackend.core.tasklist.app.controller.response;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.task.TaskState;
import java.util.UUID;

record TaskResponseDto(
    UUID id, TaskState state, short progressTotal, int timeSpent, String name, String description) {

  public static TaskResponseDto from(TaskDbDto task) {
    return new TaskResponseDto(
        task.id(),
        task.state(),
        task.progressTotal(),
        task.timeSpent(),
        task.name(),
        task.description());
  }
}
