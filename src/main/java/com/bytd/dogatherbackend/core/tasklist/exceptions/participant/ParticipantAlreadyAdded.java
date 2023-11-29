package com.bytd.dogatherbackend.core.tasklist.exceptions.participant;

import java.util.UUID;

public class ParticipantAlreadyAdded extends IllegalArgumentException {

  public ParticipantAlreadyAdded(UUID participantId) {
    super("Participant " + participantId + " is already added to the task list");
  }
}
