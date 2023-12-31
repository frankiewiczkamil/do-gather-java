package com.bytd.dogatherbackend.core.tasklist.infra.db.fake;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.task.TaskState;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = false)
@Data
public class TaskDbDtoFakeImpl implements TaskDbDto {
  private UUID id;
  private TaskState state;
  private short progressTotal;
  private int timeSpent;
  private String name;
  private String description;
  private UUID taskListId;
}
