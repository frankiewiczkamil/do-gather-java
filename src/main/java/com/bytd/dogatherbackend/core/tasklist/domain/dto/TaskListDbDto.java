package com.bytd.dogatherbackend.core.tasklist.domain.dto;

import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Participant;
import java.util.List;
import java.util.UUID;

public interface TaskListDbDto {
  void setId(UUID id);

  UUID getId();

  void setName(String name);

  String getName();

  void setDescription(String description);

  String getDescription();

  void setCreatorId(UUID creatorId);

  UUID getCreatorId();

  void setParticipants(List<Participant> participants);

  List<Participant> getParticipants();

  void setTasks(List<TaskDbDto> tasks);

  List<TaskDbDto> getTasks();
}
