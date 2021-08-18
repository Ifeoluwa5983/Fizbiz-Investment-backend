package com.fizbiz.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "fizbiz_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

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
