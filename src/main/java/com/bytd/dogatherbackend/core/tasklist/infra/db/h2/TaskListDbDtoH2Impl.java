package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "task_list")
public class TaskListDbDtoH2Impl {
  @Id private UUID id;
  private String name;
  private String description;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "task_list_id")
  private List<PermissionDbDtoH2Impl> participants;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "task_list_id")
  private List<TaskDbDtoH2Impl> tasks;

  private UUID creatorId;
}
