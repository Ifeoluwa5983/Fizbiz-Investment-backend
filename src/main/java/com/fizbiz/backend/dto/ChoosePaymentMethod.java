package com.fizbiz.backend.dto;

import com.fizbiz.backend.models.PaymentMethod;
import lombok.Data;

@Data
public class ChoosePaymentMethod {

    private String investmentId;

    private PaymentMethod paymentMethod;
}
