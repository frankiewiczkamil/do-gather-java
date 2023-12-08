package com.bytd.dogatherbackend.core.tasklist.domain.dto.command;

import java.util.UUID;
import java.util.function.Supplier;

public record CreateTaskDto(UUID id, String name, String description, UUID taskListId) {

  public CreateTaskDto withId(Supplier<UUID> taskIdGenerator) {
    if (id != null) {
      return this;
    } else {
      return new CreateTaskDto(taskIdGenerator.get(), name, description, taskListId);
    }
  }
}
