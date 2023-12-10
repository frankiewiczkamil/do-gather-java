package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "permission")
public class PermissionDbDtoH2Impl {
  @Id private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;

  @Column(name = "participant_id")
  private UUID participantId;

  @Column(name = "task_list_id")
  private UUID taskListId;
}
