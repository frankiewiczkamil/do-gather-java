package com.bytd.dogatherbackend.core.tasklist.domain.dto;

import com.bytd.dogatherbackend.core.tasklist.domain.model.task.TaskState;
import java.util.UUID;

public interface TaskDbDto {
  void id(UUID id);

  UUID id();

  void state(TaskState state);

  TaskState state();

  void progressTotal(short progressTotal);

  short progressTotal();

  void timeSpent(int timeSpent);

  int timeSpent();

  void name(String name);

  String name();

  void description(String description);

  String description();

  void taskListId(UUID taskListId);

  UUID taskListId();
}
