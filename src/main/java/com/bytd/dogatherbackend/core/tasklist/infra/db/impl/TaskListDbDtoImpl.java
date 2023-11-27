package com.bytd.dogatherbackend.core.tasklist.infra.db.impl;

import com.bytd.dogatherbackend.core.tasklist.Participant;
import com.bytd.dogatherbackend.core.tasklist.infra.db.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.infra.db.TaskListDbDto;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class TaskListDbDtoImpl implements TaskListDbDto {
  private UUID id;
  private String name;
  private String description;
  private List<Participant> participants;
  private List<TaskDbDto> tasks;
  private UUID creatorId;
}
