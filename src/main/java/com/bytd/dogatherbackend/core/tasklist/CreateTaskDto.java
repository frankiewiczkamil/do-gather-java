package com.bytd.dogatherbackend.core.tasklist;

import java.util.UUID;

public record CreateTaskDto(UUID id, String name, String description, UUID taskListId) {}
