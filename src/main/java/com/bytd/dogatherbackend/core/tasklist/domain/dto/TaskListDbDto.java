package com.bytd.dogatherbackend.core.tasklist.domain.dto;

import java.util.List;
import java.util.UUID;

public interface TaskListDbDto {
  void setId(UUID id);

  UUID getId();

  void setName(String name);

  String getName();

  void setDescription(String description);

  String getDescription();

  void setCreatorId(UUID creatorId);

  UUID getCreatorId();

  void setPermissions(List<PermissionDbDto> participants);

  List<PermissionDbDto> getPermissions();

  void setTasks(List<TaskDbDto> tasks);

  List<TaskDbDto> getTasks();
}
