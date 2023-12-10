package com.bytd.dogatherbackend.core.tasklist.domain.dto;

import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import java.util.UUID;

public interface PermissionDbDto {
  void id(UUID id);

  UUID id();

  void taskListId(UUID taskListId);

  UUID taskListId();

  void participantId(UUID participantId);

  UUID participantId();

  void role(Role role);

  Role role();
}
