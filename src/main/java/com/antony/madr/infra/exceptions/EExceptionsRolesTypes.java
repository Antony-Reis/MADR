package com.antony.madr.infra.exceptions;

public enum EExceptionsRolesTypes {
    Novelist("Novelist"),
    Book("Book"),
    User("User");

    private String type;

    EExceptionsRolesTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
