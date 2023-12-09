package com.bytd.dogatherbackend.core.tasklist.app.controller;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
import com.bytd.dogatherbackend.core.tasklist.app.controller.response.TaskListResponseDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskListDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-list")
public class TaskListController {
  private final TaskListService taskListService;

  public TaskListController(TaskListService taskListService) {
    this.taskListService = taskListService;
  }

  @GetMapping("{taskListId}")
  public Optional<TaskListResponseDto> getTaskListById(@PathVariable UUID taskListId) {
    return taskListService.getTaskList(taskListId).map(TaskListResponseDto::from);
  }

  @PostMapping()
  public TaskListResponseDto createTaskList(@RequestBody CreateTaskListDto taskList) {
    return TaskListResponseDto.from(taskListService.createTaskList(taskList));
  }
}
