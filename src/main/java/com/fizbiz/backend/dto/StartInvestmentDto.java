package com.fizbiz.backend.dto;

import com.fizbiz.backend.models.InvestmentType;
import lombok.Data;

@Data
public class StartInvestmentDto {

    private Long userId;

    private double capital;

    private InvestmentType investmentType;
}
