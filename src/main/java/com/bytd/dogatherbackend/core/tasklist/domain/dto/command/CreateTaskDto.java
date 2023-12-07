package com.bytd.dogatherbackend.core.tasklist.domain.dto.command;

import java.util.UUID;

public record CreateTaskDto(UUID id, String name, String description, UUID taskListId) {

  public CreateTaskDto withId() {
    if (id != null) {
      return this;
    } else {
      return new CreateTaskDto(UUID.randomUUID(), name, description, taskListId);
    }
  }
}
