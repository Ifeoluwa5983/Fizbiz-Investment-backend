package com.fizbiz.backend.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document
@Data
public class ApplicationUser {

    private String id;

    private String firstName;

    private String lastName;

    private String pin;

    private String emailAddress;

    private String phoneNumber;

    private String password;

    private LocalDate dateOfBirth;

    private Gender gender;

    private double totalBalance;

    private double overallCapital;

    private String homeAddress;

    private String token;

    private Role role;

    private String modifiedDate;

    private String registeredDate;

    private Boolean isActive;

    private Boolean isVerified;
}
