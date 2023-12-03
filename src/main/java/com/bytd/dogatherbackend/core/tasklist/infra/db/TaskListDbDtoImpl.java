package com.bytd.dogatherbackend.core.tasklist.infra.db;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
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
public class TaskListDbDtoImpl implements TaskListDbDto {
  @Id private UUID id;
  private String name;
  private String description;
  @Transient private List<Participant> participants;
  @Transient private List<TaskDbDto> tasks;
  //  @OneToMany private List<TaskDbDto> tasks;
  private UUID creatorId;
}
