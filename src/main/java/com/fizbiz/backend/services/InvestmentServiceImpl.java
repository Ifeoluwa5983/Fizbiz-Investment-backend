package com.fizbiz.backend.services;

import com.fizbiz.backend.dto.ChoosePaymentMethod;
import com.fizbiz.backend.dto.PaymentLink;
import com.fizbiz.backend.dto.StartInvestmentDto;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.models.ApplicationUser;
import com.fizbiz.backend.models.Investment;
import com.fizbiz.backend.models.PaymentMethod;
import com.fizbiz.backend.models.Status;
import com.fizbiz.backend.repositories.ApplicationUserRepository;
import com.fizbiz.backend.repositories.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvestmentServiceImpl implements InvestmentService{

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
        investment.setInvestmentType(startInvestmentDto.getInvestmentType());
        investment.setCapital(startInvestmentDto.getCapital());
        investment.setUserId(startInvestmentDto.getUserId());
        investment.setInvestmentDate(LocalDate.now());
        investment.setStatus(Status.Pending);
        if (startInvestmentDto.getPaymentMethod() == PaymentMethod.valueOf(PaymentMethod.BitcoinFund.toString())){
            paymentLink.setWalletAddress("bitcoin");
            investment.setPaymentMethod(startInvestmentDto.getPaymentMethod());
        }
        else if (startInvestmentDto.getPaymentMethod() == PaymentMethod.valueOf(PaymentMethod.EthereumFund.toString())) {
            paymentLink.setWalletAddress("ethereum");
            investment.setPaymentMethod(startInvestmentDto.getPaymentMethod());
        }
        else if (startInvestmentDto.getPaymentMethod() == PaymentMethod.valueOf(PaymentMethod.TetherFund.toString())) {
            paymentLink.setWalletAddress("tether");
            investment.setPaymentMethod(startInvestmentDto.getPaymentMethod());
        }
        else {
            throw new FizbizException("That payment method does not exists");
        }

        paymentLink.setPaymentMethod(startInvestmentDto.getPaymentMethod().toString());
        investmentRepository.save(investment);
        return paymentLink;
    }


    @Override
    public List<Investment> findAllInvestment() {
        return investmentRepository.findAll();
    }

    @Override
    public Investment findInvestmentById(String id) throws FizbizException {
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
}
