package com.bytd.dogatherbackend.core.tasklist.infra.db;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Participant;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_list")
public class TaskListDbDtoImpl implements TaskListDbDto<TaskDbDtoImpl> {
  @Id private UUID id;
  private String name;
  private String description;
  @Transient private List<Participant> participants;

  @OneToMany
  @JoinColumn(name = "task_list_id")
  private List<TaskDbDtoImpl> tasks;

  private UUID creatorId;
}
