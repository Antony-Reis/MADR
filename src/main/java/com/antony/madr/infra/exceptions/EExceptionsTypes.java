package com.antony.madr.infra.exceptions;

public enum EExceptionsTypes {
    Novelist("Novelist"),
    Book("Book"),
    User("User");

    private String type;

    EExceptionsTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
