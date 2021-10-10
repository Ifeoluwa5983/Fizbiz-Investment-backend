package com.fizbiz.backend.dto;

import lombok.Data;

@Data
public class PaymentLink {

    private String walletAddress;

    private String paymentMethod;
    private Double initialCapital;
    private String status;
    private Double returns;
}
