package com.fizbiz.backend.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    Male("Male"),
    Female("Female");


    public final String role;

    Gender(String status){
        this.role = status;
    }

    @JsonValue
        public String getStatus() {
            return role;
        }
    }
