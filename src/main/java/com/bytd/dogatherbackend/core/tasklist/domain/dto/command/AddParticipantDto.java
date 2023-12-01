package com.bytd.dogatherbackend.core.tasklist.domain.dto.command;

import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import java.util.List;
import java.util.UUID;

public record AddParticipantDto(
    UUID participantId, List<Role> roles, UUID authorId, UUID taskListId) {}
