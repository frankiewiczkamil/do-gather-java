package com.bytd.dogatherbackend.core.tasklist.domain.model.participant;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import java.util.UUID;
import java.util.function.Supplier;

public record Permission(UUID participantId, Role role) {

  public PermissionDbDto toDbDto(Supplier<PermissionDbDto> dtoSupplier, UUID taskListId) {
    var dto = dtoSupplier.get();
    dto.participantId(participantId);
    dto.role(role);

    dto.taskListId(taskListId);

    return dto;
  }

  public static Permission fromDbDto(PermissionDbDto dto) {
    return new Permission(dto.participantId(), dto.role());
  }
}
