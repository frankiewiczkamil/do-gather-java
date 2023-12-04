package com.bytd.dogatherbackend.core.tasklist.infra.db.fake;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Participant;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class TaskListDbDtoFakeImpl implements TaskListDbDto<TaskDbDtoFakeImpl> {
  private UUID id;
  private String name;
  private String description;
  private List<Participant> participants;
  private List<TaskDbDtoFakeImpl> tasks;
  private UUID creatorId;
}
