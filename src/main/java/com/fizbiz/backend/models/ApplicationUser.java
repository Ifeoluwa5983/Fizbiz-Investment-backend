package com.fizbiz.backend.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
