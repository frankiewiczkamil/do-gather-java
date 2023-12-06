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
    instance.id = dto.getId();
    instance.name = dto.getName();
    instance.description = dto.getDescription();
    instance.taskListId = dto.getTaskListId();
    instance.state = dto.getState();
    instance.progressTotal = dto.getProgressTotal();
    instance.timeSpent = dto.getTimeSpent();
    return instance;
  }

  boolean isIdConflict(UUID id) {
    return this.id.equals(id);
  }

  public TaskDbDto toDbDto(Supplier<TaskDbDto> dtoSupplier) {
    var dto = dtoSupplier.get();
    dto.setId(id);
    dto.setState(state);
    dto.setProgressTotal(progressTotal);
    dto.setTimeSpent(timeSpent);
    dto.setName(name);
    dto.setDescription(description);
    dto.setTaskListId(taskListId);
    return dto;
  }

  public Task withId() {
    if (this.id == null) this.id = UUID.randomUUID();
    return this;
  }
}
