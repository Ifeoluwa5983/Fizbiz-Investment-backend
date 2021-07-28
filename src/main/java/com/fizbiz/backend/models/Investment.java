package com.fizbiz.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Investment {

    @Id
    private String id;

    private double capital;

    private InvestmentType investmentType;

    private PaymentMethod paymentMethod;

    private LocalDate investmentDate;

    private Status status;

    private String userId;

    private double returns;

}
