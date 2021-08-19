package com.fizbiz.backend.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {

    USER("USER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");

    public final String role;

    Role(String status){
        this.role = status;
    }

    @JsonValue
    public String getStatus() {
        return role;
    }
}
