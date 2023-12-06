package com.bytd.dogatherbackend.core.tasklist.app;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
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
  public Optional<TaskListDbDto> getTaskListById(@PathVariable String taskListId) {
    return taskListService.getTaskList(UUID.fromString(taskListId));
  }

  @PostMapping()
  public TaskListDbDto createTaskList(@RequestBody CreateTaskListDto taskList) {
    return taskListService.createTaskList(taskList);
  }
}
