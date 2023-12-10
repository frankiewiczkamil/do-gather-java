package com.bytd.dogatherbackend.core.tasklist.domain;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.task.TaskState;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Data
public class Task {
  private UUID id;
  private TaskState state;
  private short progressTotal;
  private int timeSpent;

  private String name;
  private String description;
  private UUID taskListId;

  public static Task create(CreateTaskDto dto) {
    var instance = new Task();
    instance.id = dto.id();
    instance.name = dto.name();
    instance.description = dto.description();
    instance.taskListId = dto.taskListId();
    instance.state = TaskState.NEW;
    instance.progressTotal = 0;
    instance.timeSpent = 0;
    return instance;
  }

  public static Task fromDbDto(TaskDbDto dto) {
    var instance = new Task();
    instance.id = dto.id();
    instance.name = dto.name();
    instance.description = dto.description();
    instance.taskListId = dto.taskListId();
    instance.state = dto.state();
    instance.progressTotal = dto.progressTotal();
    instance.timeSpent = dto.timeSpent();
    return instance;
  }

  boolean isIdConflict(UUID id) {
    return this.id.equals(id);
  }

  public TaskDbDto toDbDto(Supplier<TaskDbDto> dtoSupplier) {
    var dto = dtoSupplier.get();
    dto.id(id);
    dto.state(state);
    dto.progressTotal(progressTotal);
    dto.timeSpent(timeSpent);
    dto.name(name);
    dto.description(description);
    dto.taskListId(taskListId);
    return dto;
  }
}
