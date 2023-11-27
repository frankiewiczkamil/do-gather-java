package com.bytd.dogatherbackend.core.tasklist.infra.db.impl;

import com.bytd.dogatherbackend.core.tasklist.TaskState;
import com.bytd.dogatherbackend.core.tasklist.infra.db.TaskDbDto;
import java.util.UUID;
import lombok.Data;

@Data
public class TaskDbDtoImpl implements TaskDbDto {
  private UUID id;
  private TaskState state;
  private short progressTotal;
  private int timeSpent;

  private String name;
  private String description;
  private UUID taskListId;
}
