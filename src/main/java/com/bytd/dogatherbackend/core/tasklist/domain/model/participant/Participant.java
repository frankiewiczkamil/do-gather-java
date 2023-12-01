package com.bytd.dogatherbackend.core.tasklist.domain.model.participant;

import java.util.List;
import java.util.UUID;

public record Participant(UUID participantId, List<Role> roles) {}
