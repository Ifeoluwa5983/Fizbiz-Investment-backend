package com.fizbiz.backend.controller;

import com.fizbiz.backend.dto.ChoosePaymentMethod;
import com.fizbiz.backend.dto.PaymentLink;
import com.fizbiz.backend.dto.StartInvestmentDto;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.models.Investment;
import com.fizbiz.backend.response.ResponseDetailsWithObject;
import com.fizbiz.backend.services.InvestmentServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/investment")
public class InvestmentController {

    @Autowired
    InvestmentServiceImpl investmentService;

    @PostMapping("/startInvestment")
    public ResponseEntity<?> startInvestment(@Valid @RequestBody Investment startInvestmentDto) throws FizbizException {
        PaymentLink paymentLink = investmentService.startInvestment(startInvestmentDto);
        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "Your Investment has started successfully", paymentLink, HttpStatus.OK.toString());
        return ResponseEntity.status(200).body(responseDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findInvestmentById(@PathVariable String id) throws FizbizException {
        Investment investment = investmentService.findInvestmentById(id);
        return new ResponseEntity<>(investment, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllInvestments() {
        List<Investment> investments = investmentService.findAllInvestment();
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }

    @GetMapping("/findAllInvestmentsOfAUser/{id}")
    public ResponseEntity<?> findAllInvestmentsOfAUser(@PathVariable Long id) throws FizbizException {
        List<Investment> investments = investmentService.findAllInvestmentOfAUser(id);
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }

}
