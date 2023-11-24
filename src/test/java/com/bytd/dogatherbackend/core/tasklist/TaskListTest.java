package com.bytd.dogatherbackend.core.tasklist;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskListTest {
  UUID taskListId = UUID.randomUUID();
  CreateTaskListDto createTaskListDto =
      new CreateTaskListDto(taskListId, "name", "description", UUID.randomUUID());

  @Test
  void shouldCreateTaskListInstance() {
    assertDoesNotThrow(() -> TaskList.create(createTaskListDto));
  }

  @Test
  void shouldAllowAddingTasksToTheList() {
    var taskList = TaskList.create(createTaskListDto);
    var task1 = new CreateTaskDto(UUID.randomUUID(), "name", "description");
    var task2 = new CreateTaskDto(UUID.randomUUID(), "name", "description");

    assertDoesNotThrow(() -> taskList.addTask(task1));
    assertDoesNotThrow(() -> taskList.addTask(task2));
  }

  @Test
  void shouldNotAllowAddingTheSameTaskTwice() {
    var createTaskDto = new CreateTaskDto(UUID.randomUUID(), "name", "description");
    var taskList = TaskList.create(createTaskListDto);

    taskList.addTask(createTaskDto);
    assertThrows(IllegalArgumentException.class, () -> taskList.addTask(createTaskDto));
  }
}
