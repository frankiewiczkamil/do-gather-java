package com.bytd.dogatherbackend.core.tasklist;

import java.util.UUID;

public record CreateTaskListDto(UUID id, String name, String description, UUID creatorId) {
  public CreateTaskListDto withId(UUID id) {
    return new CreateTaskListDto(id, name, description, creatorId);
  }
}
