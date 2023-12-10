package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.domain.model.task.TaskState;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "task")
public class TaskDbDtoH2Impl {
  @Id private UUID id;

  @Enumerated(EnumType.STRING)
  private TaskState state;

  private short progressTotal;
  private int timeSpent;
  private String name;
  private String description;

  @Column(name = "task_list_id")
  private UUID taskListId;
}
