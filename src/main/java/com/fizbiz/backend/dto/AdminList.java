package com.fizbiz.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminList {

    private Double overallCapital;

    private Double overallBalance;

    private Double overallReturns;
}
