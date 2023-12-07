package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "permission")
public class PermissionDbDtoH2Impl implements PermissionDbDto {
  @Id private UUID id;

  //    @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private String role;

  @Column(name = "participant_id")
  private UUID participantId;

  @Column(name = "task_list_id")
  private UUID taskListId;
}
