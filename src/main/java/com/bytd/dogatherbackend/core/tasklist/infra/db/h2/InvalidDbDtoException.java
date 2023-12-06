package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

public class InvalidDbDtoException extends IllegalArgumentException {
    public InvalidDbDtoException(String message) {
        super(message);
    }
}
