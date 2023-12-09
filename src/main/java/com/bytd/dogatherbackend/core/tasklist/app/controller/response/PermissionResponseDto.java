package com.bytd.dogatherbackend.core.tasklist.app.controller.response;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import java.util.UUID;

public record PermissionResponseDto(UUID id, Role role, UUID participantId) {

  public static PermissionResponseDto from(PermissionDbDto permission) {
    UUID id = permission.getId();
    Role role = permission.getRole();
    UUID participantId = permission.getParticipantId();
    return new PermissionResponseDto(id, role, participantId);
  }
}
