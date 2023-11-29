package com.bytd.dogatherbackend.core.tasklist.exceptions.participant;

import java.util.UUID;

public class AuthorIsNotAParticipant extends IllegalArgumentException {

  public AuthorIsNotAParticipant(UUID authorId) {
    super("Author with id " + authorId + " is not a participant of the task list");
  }
}
