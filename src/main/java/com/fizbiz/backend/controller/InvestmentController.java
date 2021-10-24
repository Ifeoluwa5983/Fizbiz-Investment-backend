package com.fizbiz.backend.controller;

import com.fizbiz.backend.dto.AdminList;
import com.fizbiz.backend.dto.PaymentLink;
import com.fizbiz.backend.dto.UserList;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.models.Account;
import com.fizbiz.backend.models.Investment;
import com.fizbiz.backend.response.ResponseDetails;
import com.fizbiz.backend.response.ResponseDetailsWithObject;
import com.fizbiz.backend.services.InvestmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> findInvestmentById(@PathVariable Long id) throws FizbizException {
        Investment investment = investmentService.findInvestmentById(id);
        return new ResponseEntity<>(investment, HttpStatus.OK);
    }


    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN, SUPER_ADMIN')")
    public ResponseEntity<?> findAllInvestments() {
        List<Investment> investments = investmentService.findAllInvestment();
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }

    @GetMapping("/findAllInvestmentsOfAUser/{id}")
    public ResponseEntity<?> findAllInvestmentsOfAUser(@PathVariable Long id) throws FizbizException {
        List<Investment> investments = investmentService.findAllInvestmentOfAUser(id);
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInvestment(@Valid @RequestBody Investment investment) throws FizbizException {
        if (investment.getId() == null) {
            throw new FizbizException("User id cannot be null");
        }
        investmentService.updateInvestment(investment);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Investment updated successfully", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> changeInvestmentStatus(@PathVariable Long id) {
        investmentService.changeInvestmentStatus(id);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Your Investment status has been changed successfully", HttpStatus.OK.toString());
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @PostMapping("/optOut")
    public ResponseEntity<?> optOut(@RequestBody Account account) throws FizbizException {
        investmentService.optOut(account);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Sit back for 48 hours, your money is on its way", HttpStatus.OK.toString());
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @GetMapping("/returns/{id}")
    public ResponseEntity<?> setTotalReturns(@PathVariable Long id) throws FizbizException {
        if (id == null){
            throw new FizbizException("Id cannot be null");
        }
        investmentService.setTotalReturns(id);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Your Investment status has been changed successfully", HttpStatus.OK.toString());
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @GetMapping("/getInvestmentSummary/")
    public ResponseEntity<?> getInvestmentSummary() {
        AdminList adminList = investmentService.getInvestmentSummary();
        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "Hurray!!",adminList, HttpStatus.OK.toString());
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @GetMapping("/getUserInvestmentSummary/{userId}")
    public ResponseEntity<?> getUserInvestmentSummary(@PathVariable Long userId) {
        UserList userList = investmentService.getUserInvestmentSummary(userId);
        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "Hurray!!",userList, HttpStatus.OK.toString());
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

}
