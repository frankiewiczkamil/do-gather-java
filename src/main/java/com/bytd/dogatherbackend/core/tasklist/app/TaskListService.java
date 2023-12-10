package com.bytd.dogatherbackend.core.tasklist.app;

import com.bytd.dogatherbackend.core.tasklist.domain.*;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.*;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.AddParticipantDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskListDto;
import com.bytd.dogatherbackend.core.tasklist.exceptions.TaskListDoesNotExist;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskListService {
  private TaskListRepository taskListRepository;
  private Supplier<TaskListDbDto> taskListDbDtoSupplier;
  private Supplier<TaskDbDto> taskDbDtoSupplier;
  private Supplier<PermissionDbDto> permissionDbDtoSupplier;
  private Supplier<UUID> taskListIdGenerator;
  private Supplier<UUID> taskIdGenerator;
  private Supplier<UUID> permissionIdGenerator;

  public TaskListDbDto createTaskList(CreateTaskListDto dto) {
    TaskList taskList =
        TaskList.create(
            dto.generateIdIfNotProvided(taskListIdGenerator).withTasks(taskIdGenerator));
    var taskListDbDto = toTaskListDbDto(taskList);

    taskListDbDto
        .permissions()
        .forEach(
            permission -> {
              permission.taskListId(taskListDbDto.id());
              permission.id(permissionIdGenerator.get());
            });
    taskListRepository.save(taskListDbDto);
    return taskListDbDto;
  }

  public void addTask(CreateTaskDto dto) {
    Optional<TaskListDbDto> taskListDbDto = taskListRepository.findById(dto.taskListId());
    if (taskListDbDto.isEmpty()) {
      throw new TaskListDoesNotExist(dto.taskListId());
    } else {
      TaskList taskList = TaskList.fromDbDto(taskListDbDto.get());
      taskList.addTask(dto.withId(taskIdGenerator));
      taskListRepository.save(toTaskListDbDto(taskList));
    }
  }

  public void addParticipant(AddParticipantDto dto) {
    Optional<TaskListDbDto> taskListDbDto = taskListRepository.findById(dto.taskListId());
    if (taskListDbDto.isEmpty()) {
      throw new TaskListDoesNotExist(dto.taskListId());
    } else {
      TaskList taskList = TaskList.fromDbDto(taskListDbDto.get());
      taskList.addParticipant(dto);
      taskListRepository.save(toTaskListDbDto(taskList));
    }
  }

  private TaskListDbDto toTaskListDbDto(TaskList taskList) {
    return taskList.toDbDto(taskListDbDtoSupplier, taskDbDtoSupplier, permissionDbDtoSupplier);
  }

  public Optional<TaskListDbDto> getTaskList(UUID taskListId) {
    var taskListDbDto = taskListRepository.findById(taskListId);
    if (taskListDbDto.isEmpty()) {
      throw new IllegalArgumentException("Task list does not exist");
    } else {
      return taskListDbDto;
    }
  }
}
