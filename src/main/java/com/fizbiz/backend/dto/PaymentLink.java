package com.fizbiz.backend.dto;

import lombok.Data;

@Data
public class PaymentLink {

    private String walletAddress;

    private String paymentMethod;
}
