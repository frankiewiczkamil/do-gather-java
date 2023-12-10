package com.bytd.dogatherbackend.core.tasklist.domain.dto;

import java.util.List;
import java.util.UUID;

public interface TaskListDbDto {
  void id(UUID id);

  UUID id();

  void name(String name);

  String name();

  void description(String description);

  String description();

  void creatorId(UUID creatorId);

  UUID creatorId();

  void permissions(List<PermissionDbDto> participants);

  List<PermissionDbDto> permissions();

  void tasks(List<TaskDbDto> tasks);

  List<TaskDbDto> tasks();
}
