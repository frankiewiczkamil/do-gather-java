package com.bytd.dogatherbackend.core.tasklist;

import java.util.List;
import java.util.UUID;

public record Participant(UUID participantId, List<Role> roles) {}
