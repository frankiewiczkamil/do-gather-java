package com.bytd.dogatherbackend.core.users;

import java.util.UUID;

public record User(UUID id, String email, String name, String password) {}
