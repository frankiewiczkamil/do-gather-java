package com.bytd.dogatherbackend.core.tasklist.domain.dto;

import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import java.util.UUID;

public interface PermissionDbDto {
  void setId(UUID id);

  UUID getId();

  void setTaskListId(UUID taskListId);

  UUID getTaskListId();

  void setParticipantId(UUID participantId);

  UUID getParticipantId();

  void setRole(Role role);

  Role getRole();
}
