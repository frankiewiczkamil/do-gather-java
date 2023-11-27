package com.bytd.dogatherbackend.core.tasklist.infra.db;

import com.bytd.dogatherbackend.core.tasklist.TaskState;
import java.util.UUID;

public interface TaskDbDto {
  void setId(UUID id);

  UUID getId();

  void setState(TaskState state);

  TaskState getState();

  void setProgressTotal(short progressTotal);

  short getProgressTotal();

  void setTimeSpent(int timeSpent);

  int getTimeSpent();

  void setName(String name);

  String getName();

  void setDescription(String description);

  String getDescription();

  void setTaskListId(UUID taskListId);

  UUID getTaskListId();
}
