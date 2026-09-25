package com.antony.madr.users;

public enum EUserRoles {
    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    EUserRoles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
