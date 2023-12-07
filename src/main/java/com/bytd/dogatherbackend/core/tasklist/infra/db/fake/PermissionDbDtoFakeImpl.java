package com.bytd.dogatherbackend.core.tasklist.infra.db.fake;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionDbDtoFakeImpl implements PermissionDbDto {
  private UUID id;

  private UUID participantId;
  private String role; // todo
  private UUID taskListId;
}
