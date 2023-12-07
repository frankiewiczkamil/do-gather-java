package com.bytd.dogatherbackend.core.tasklist.infra.db.fake;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class TaskListDbDtoFakeImpl implements TaskListDbDto {
  private UUID id;
  private String name;
  private String description;
  private List<PermissionDbDto> permissions;
  private List<TaskDbDto> tasks;
  private UUID creatorId;
}
