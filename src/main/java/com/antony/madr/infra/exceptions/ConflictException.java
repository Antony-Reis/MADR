package com.antony.madr.infra.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(EExceptionsRolesTypes type) {
        super(type + " is already in MADR.");
    }
}
