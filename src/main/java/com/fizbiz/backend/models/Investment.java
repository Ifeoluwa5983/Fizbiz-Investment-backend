package com.fizbiz.backend.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private double capital;

    private InvestmentType investmentType;

    private PaymentMethod paymentMethod;

    private LocalDate investmentDate;

    private Status status;

    private String userId;

    private double returns;

}
