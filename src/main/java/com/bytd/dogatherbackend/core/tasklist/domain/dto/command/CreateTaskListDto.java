package com.bytd.dogatherbackend.core.tasklist.domain.dto.command;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public record CreateTaskListDto(
    UUID id, String name, String description, UUID creatorId, List<CreateTaskDto> tasks) {
  public CreateTaskListDto generateIdIfNotProvided(Supplier<UUID> idGenerator) {
    if (id != null) {
      return this;
    } else {
      return new CreateTaskListDto(idGenerator.get(), name, description, creatorId, tasks);
    }
  }

  public CreateTaskListDto withTasks(Supplier<UUID> taskIdGenerator) {
    List<CreateTaskDto> taskz =
        tasks == null
            ? List.of()
            : tasks.stream().map(createTaskDto -> createTaskDto.withId(taskIdGenerator)).toList();
    return new CreateTaskListDto(id, name, description, creatorId, taskz);
  }
}
