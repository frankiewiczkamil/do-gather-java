package com.bytd.dogatherbackend.core.tasklist.domain.dto;

import java.util.UUID;

public interface PermissionDbDto {
  void setId(UUID id);

  UUID getId();

  void setTaskListId(UUID taskListId);

  UUID getTaskListId();

  void setParticipantId(UUID participantId);

  UUID getParticipantId();

  void setRole(String role); // todo: change to Role

  String getRole();
}
