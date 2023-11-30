package com.bytd.dogatherbackend.core.tasklist.infra.db.impl;

import com.bytd.dogatherbackend.core.tasklist.Participant;
import com.bytd.dogatherbackend.core.tasklist.app.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.app.TaskListDbDto;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskListDbDtoImpl implements TaskListDbDto {
  private UUID id;
  private String name;
  private String description;
  private List<Participant> participants;
  private List<TaskDbDto> tasks;
  private UUID creatorId;
}
