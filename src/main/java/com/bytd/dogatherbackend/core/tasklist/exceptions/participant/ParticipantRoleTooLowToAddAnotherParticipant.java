package com.bytd.dogatherbackend.core.tasklist.exceptions.participant;

public class ParticipantRoleTooLowToAddAnotherParticipant extends RuntimeException {
  public ParticipantRoleTooLowToAddAnotherParticipant(String role) {
    super("Participant role (" + role + ") is too low to add another participant");
  }
}
