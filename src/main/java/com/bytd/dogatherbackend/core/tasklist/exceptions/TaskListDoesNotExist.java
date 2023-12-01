package com.bytd.dogatherbackend.core.tasklist.exceptions;

import java.util.UUID;

public class TaskListDoesNotExist extends IllegalArgumentException {
  public TaskListDoesNotExist(UUID taskListId) {
    super("Task list with id " + taskListId + " does not exist");
  }
}
