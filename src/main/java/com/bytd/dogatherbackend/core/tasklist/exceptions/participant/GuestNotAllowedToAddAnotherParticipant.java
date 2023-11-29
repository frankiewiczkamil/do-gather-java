package com.bytd.dogatherbackend.core.tasklist.exceptions.participant;

import java.util.UUID;

public class GuestNotAllowedToAddAnotherParticipant extends RuntimeException {
  public GuestNotAllowedToAddAnotherParticipant(UUID participantId) {
    super("Guest " + participantId + " is not allowed to add another participant");
  }
}
