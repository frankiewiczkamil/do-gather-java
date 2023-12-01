package com.bytd.dogatherbackend.core.tasklist.web;

import com.bytd.dogatherbackend.core.tasklist.CreateTaskListDto;
import com.bytd.dogatherbackend.core.tasklist.app.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
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
  public Optional<TaskListDbDto> getTaskListById(@PathVariable String taskListId) {
    return taskListService.getTaskList(UUID.fromString(taskListId));
  }

  @PostMapping()
  public TaskListDbDto createTaskList(@RequestBody CreateTaskListDto taskList) {
    return taskListService.createTaskList(taskList);
  }
}
