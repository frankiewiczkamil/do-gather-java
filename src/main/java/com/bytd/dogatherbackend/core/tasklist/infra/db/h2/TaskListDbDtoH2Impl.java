package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Participant;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "task_list")
public class TaskListDbDtoH2Impl implements TaskListDbDto<TaskDbDtoH2Impl> {
  @Id private UUID id;
  private String name;
  private String description;
  @Transient private List<Participant> participants;

  @OneToMany
  @JoinColumn(name = "task_list_id")
  private List<TaskDbDtoH2Impl> tasks;

  private UUID creatorId;
}
