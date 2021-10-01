package com.fizbiz.backend.services;

import com.fizbiz.backend.dto.ChoosePaymentMethod;
import com.fizbiz.backend.dto.PaymentLink;
import com.fizbiz.backend.dto.StartInvestmentDto;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.models.Investment;

import java.util.List;

public interface InvestmentService {

    PaymentLink startInvestment(Investment startInvestmentDto) throws FizbizException;

    List<Investment> findAllInvestment();

    Investment findInvestmentById(Long id) throws FizbizException;

    List<Investment> findAllInvestmentOfAUser(Long userId) throws FizbizException;
}
