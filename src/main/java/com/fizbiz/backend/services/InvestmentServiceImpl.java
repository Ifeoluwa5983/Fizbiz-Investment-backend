package com.fizbiz.backend.services;

import com.fizbiz.backend.dto.ChoosePaymentMethod;
import com.fizbiz.backend.dto.PaymentLink;
import com.fizbiz.backend.dto.StartInvestmentDto;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.models.Investment;
import com.fizbiz.backend.models.InvestmentType;
import com.fizbiz.backend.models.PaymentMethod;
import com.fizbiz.backend.models.Status;
import com.fizbiz.backend.repositories.ApplicationUserRepository;
import com.fizbiz.backend.repositories.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    @Autowired
    InvestmentRepository investmentRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Override
    public PaymentLink startInvestment(Investment startInvestmentDto) throws FizbizException {
        if (!applicationUserRepository.existsById(startInvestmentDto.getUserId())) {
            throw new FizbizException("User with the given id does not exist");
        }
        Investment investment = new Investment();
        PaymentLink paymentLink = new PaymentLink();
        investment.setCapital(startInvestmentDto.getCapital());
        investment.setUserId(startInvestmentDto.getUserId());
        investment.setTimeOfInvestment(LocalDate.now());
        investment.setStatus(Status.Pending);
        if (startInvestmentDto.getPaymentMethod() == PaymentMethod.valueOf(PaymentMethod.BitcoinFund.toString())){
            paymentLink.setWalletAddress("1AFRpuX51yMjbNrstJ92k1nYj4YJcYeixo");
            investment.setPaymentMethod(startInvestmentDto.getPaymentMethod());
        }
        else if (startInvestmentDto.getPaymentMethod() == PaymentMethod.valueOf(PaymentMethod.EthereumFund.toString())) {
            paymentLink.setWalletAddress("0xf49fe5689ccd418df1740a5ce63101ed4bf14beb");
            investment.setPaymentMethod(startInvestmentDto.getPaymentMethod());
        }
        else if (startInvestmentDto.getPaymentMethod() == PaymentMethod.valueOf(PaymentMethod.TetherFund.toString())) {
            paymentLink.setWalletAddress("TDSrDtFYbmmx9qd3ZhxQo4UkEtdnbRMs4q");
            investment.setPaymentMethod(startInvestmentDto.getPaymentMethod());
        }
        else if (startInvestmentDto.getPaymentMethod() == PaymentMethod.valueOf(PaymentMethod.BankTransfer.toString())) {
            paymentLink.setWalletAddress("Paystack integration");
            investment.setPaymentMethod(startInvestmentDto.getPaymentMethod());
        }
        else {
            throw new FizbizException("That payment method does not exists");
        }
        if ( investment.getCapital() >= 1000 && investment.getCapital() <= 2999 ) {
            investment.setInvestmentType(InvestmentType.Silver);
            investment.setReturns(15.0);
        } else if ( investment.getCapital() >= 3000 && investment.getCapital() <= 4999 ) {
            investment.setInvestmentType(InvestmentType.Gold);
            investment.setReturns(20.0);
        } else {
            investment.setInvestmentType(InvestmentType.Diamond);
            investment.setReturns(25.0);
        };

        paymentLink.setPaymentMethod(startInvestmentDto.getPaymentMethod().toString());
        paymentLink.setReturns(investment.getReturns());
        paymentLink.setInitialCapital(investment.getCapital());
        investmentRepository.save(investment);
        return paymentLink;
    }


    @Override
    public List<Investment> findAllInvestment() {
        return investmentRepository.findAll();
    }

    @Override
    public Investment findInvestmentById(Long id) throws FizbizException {
        if (!investmentRepository.existsById(id)){
            throw new FizbizException("The id does not exist");
        }
        return investmentRepository.findById(id).get();
    }

    @Override
    public List<Investment> findAllInvestmentOfAUser(Long userId) throws FizbizException {
        if (!applicationUserRepository.existsById(userId)){
            throw new FizbizException("The user with the id does not exist");
        }
        return investmentRepository.findAllByUserId(userId);
    }

    public void changeInvestmentStatus(Long investmentId){
        Investment investment = investmentRepository.findById(investmentId).get();
        investment.setStatus(Status.Active);
        investmentRepository.save(investment);
    }
}
