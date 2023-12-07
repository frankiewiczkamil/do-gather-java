package com.bytd.dogatherbackend.core.tasklist.infra.db.fake;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionDbDtoFakeImpl implements PermissionDbDto {
  private UUID id;

  private UUID participantId;
  private Role role;
  private UUID taskListId;
}
