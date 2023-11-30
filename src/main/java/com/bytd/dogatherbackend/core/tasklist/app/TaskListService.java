package com.bytd.dogatherbackend.core.tasklist.app;

import com.bytd.dogatherbackend.core.tasklist.*;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskListService {
  private TaskListRepository taskListRepository;
  private Supplier<TaskListDbDto> taskListDbDtoSupplier;
  private Supplier<TaskDbDto> taskDbDtoSupplier;

  public void createTaskList(CreateTaskListDto dto) {
    var taskList = TaskList.create(dto);
    var taskListDbDto = toTaskListDbDto(taskList);
    taskListRepository.save(taskListDbDto);
  }

  public void addTask(CreateTaskDto dto) {
    var taskListDbDto = taskListRepository.findById(dto.taskListId());
    if (taskListDbDto.isEmpty()) {
      throw new IllegalArgumentException("Task list does not exist");
    } else {
      var taskList = TaskList.fromDbDto(taskListDbDto.get());
      taskList.addTask(dto);
      taskListRepository.save(toTaskListDbDto(taskList));
    }
  }

  public void addParticipant(AddParticipantDto dto) {
    var taskListDbDto = taskListRepository.findById(dto.taskListId());
    if (taskListDbDto.isEmpty()) {
      throw new IllegalArgumentException("Task list does not exist");
    } else {
      var taskList = TaskList.fromDbDto(taskListDbDto.get());
      taskList.addParticipant(dto);
      taskListRepository.save(toTaskListDbDto(taskList));
    }
  }

  private TaskListDbDto toTaskListDbDto(TaskList taskList) {
    return taskList.toDbDto(taskListDbDtoSupplier, taskDbDtoSupplier);
  }
}
