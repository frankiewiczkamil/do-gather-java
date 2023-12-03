package com.bytd.dogatherbackend.core.tasklist.app;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskListDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-list")
public class TaskListController<TaskListDto extends TaskListDbDto, TaskDto extends TaskDbDto> {
  private final TaskListService<TaskListDto, TaskDto> taskListService;

  public TaskListController(TaskListService taskListService) {
    this.taskListService = taskListService;
  }

  @GetMapping("{taskListId}")
  public Optional<TaskListDto> getTaskListById(@PathVariable String taskListId) {
    return taskListService.getTaskList(UUID.fromString(taskListId));
  }

  @PostMapping()
  public TaskListDbDto createTaskList(@RequestBody CreateTaskListDto taskList) {
    return taskListService.createTaskList(taskList);
  }
}
