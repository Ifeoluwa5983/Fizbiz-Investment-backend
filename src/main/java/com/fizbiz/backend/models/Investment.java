package com.fizbiz.backend.models;

import com.fizbiz.backend.exception.FizbizException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Investment {


    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "investment_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    private Double capital;

    private InvestmentType investmentType;

    private PaymentMethod paymentMethod;

    private Status status;

    private Long userId;

    private Double returns;

    private LocalDate timeOfInvestment;

    private LocalDate returnDate;

    private String imageUrl;

    private Double totalReturns;

    private LocalDate modifiedDate;
}
