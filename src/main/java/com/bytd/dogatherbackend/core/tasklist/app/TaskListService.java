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

  public TaskListDbDto createTaskList(CreateTaskListDto dto) {
    dto = dto.id() == null ? dto.withId(UUID.randomUUID()) : dto;
    var taskList = TaskList.create(dto);
    var taskListDbDto = toTaskListDbDto(taskList);
    taskListRepository.save(taskListDbDto);
    return taskListDbDto;
  }

  public void addTask(CreateTaskDto dto) {
    var taskListDbDto = taskListRepository.findById(dto.taskListId());
    if (taskListDbDto.isEmpty()) {
      throw new TaskListDoesNotExist(dto.taskListId());
    } else {
      var taskList = TaskList.fromDbDto(taskListDbDto.get());
      taskList.addTask(dto);
      taskListRepository.save(toTaskListDbDto(taskList));
    }
  }

  public void addParticipant(AddParticipantDto dto) {
    var taskListDbDto = taskListRepository.findById(dto.taskListId());
    if (taskListDbDto.isEmpty()) {
      throw new TaskListDoesNotExist(dto.taskListId());
    } else {
      var taskList = TaskList.fromDbDto(taskListDbDto.get());
      taskList.addParticipant(dto);
      taskListRepository.save(toTaskListDbDto(taskList));
    }
  }

  private TaskListDbDto toTaskListDbDto(TaskList taskList) {
    return taskList.toDbDto(taskListDbDtoSupplier, taskDbDtoSupplier);
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
