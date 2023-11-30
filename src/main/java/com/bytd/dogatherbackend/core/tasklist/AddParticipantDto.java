package com.bytd.dogatherbackend.core.tasklist;

import java.util.List;
import java.util.UUID;

public record AddParticipantDto(
    UUID participantId, List<Role> roles, UUID authorId, UUID taskListId) {}
