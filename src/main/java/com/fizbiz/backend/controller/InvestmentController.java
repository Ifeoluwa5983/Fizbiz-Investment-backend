package com.fizbiz.backend.controller;

import com.fizbiz.backend.dto.*;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.models.Investment;
import com.fizbiz.backend.response.ResponseDetails;
import com.fizbiz.backend.response.ResponseDetailsWithObject;
import com.fizbiz.backend.services.InvestmentServiceImpl;
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
    public ResponseEntity<?> startInvestment(@Valid @RequestBody StartInvestmentDto startInvestmentDto) throws FizbizException {
        Investment investment = investmentService.startInvestment(startInvestmentDto);
        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "Your Investment has started successfully", investment, HttpStatus.OK.toString());
        return ResponseEntity.status(200).body(responseDetails);
    }

    @PostMapping("/choosePaymentMethod")
    public ResponseEntity<?> choosePaymentMethod(@Valid @RequestBody ChoosePaymentMethod choosePaymentMethod) throws FizbizException {
        PaymentLink paymentLink = investmentService.choosePaymentMethod(choosePaymentMethod);
        return ResponseEntity.status(200).body(paymentLink);
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
    public ResponseEntity<?> findAllInvestmentsOfAUser(@PathVariable String id) throws FizbizException {
        List<Investment> investments = investmentService.findAllInvestmentOfAUser(id);
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }

}
