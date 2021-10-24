package com.fizbiz.backend.services;

import com.fizbiz.backend.dto.*;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.models.*;
import com.fizbiz.backend.repositories.AccountRepository;
import com.fizbiz.backend.repositories.ApplicationUserRepository;
import com.fizbiz.backend.repositories.InvestmentRepository;
import com.fizbiz.backend.repositories.WithdrawalRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    @Autowired
    InvestmentRepository investmentRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WithdrawalRepository withdrawalRepository;

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
        paymentLink.setStatus(investment.getStatus().toString());
        paymentLink.setInvestmentType(investment.getInvestmentType().toString());
        paymentLink.setImageUrl(investment.getImageUrl());
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

    public void optOut(Account account) throws FizbizException {
        Investment investment = investmentRepository.findById(account.getInvestmentId()).get();
        if (LocalDate.now().equals(investment.getTimeOfInvestment().plusMonths(2))){
            investment.setStatus(Status.Pending);
            accountRepository.save(account);
        }
        else {
            throw new FizbizException("Sorry, You can't opt out now");
        }
    }

    public void withdrawal(Withdrawal withdrawal) throws FizbizException {
        Investment investment = investmentRepository.findById(withdrawal.getInvestmentId()).get();
        if (investment.getReturns() != 0.0){
            investment.setStatus(Status.Pending);
            withdrawalRepository.save(withdrawal);
        }
        else {
            throw new FizbizException("Sorry, You can't withdraw now");
        }
    }

    public void setTotalReturns(Long investmentId) throws FizbizException {
        Investment investment = investmentRepository.findById(investmentId).get();
        if (LocalDate.now().equals(investment.getReturnDate().plusDays(30)) && investment.getStatus() == Status.Active){
            investment.setTotalReturns(investment.getReturns() + investment.getTotalReturns());
        }
        else {
            throw new FizbizException("Its too early for an added returns");
        }
    }

    public AdminList getInvestmentSummary(){
        AdminList adminList = new AdminList();

        List<Investment> investments = investmentRepository.findAll();
        Double capital = 0.0;
        Double returns = 0.0;
        Double balance = 0.0;
        for (Investment investment: investments){
            if (investment.getStatus() == Status.Active){
                ApplicationUser user = applicationUserRepository.findById(investment.getUserId()).get();
                capital = capital + investment.getCapital();
                returns = returns + investment.getTotalReturns();
                balance = balance + user.getTotalBalance();
            }
            System.out.println(capital);
        }
        adminList.setOverallCapital(capital);
        adminList.setOverallBalance(balance);
        adminList.setOverallReturns(returns);
        return adminList;
    }

    public UserList getUserInvestmentSummary(Long userId){
        ApplicationUser user = applicationUserRepository.findById(userId).get();
        List<Investment> investments = investmentRepository.findAllByUserId(userId);
        UserList userList = new UserList();
        userList.setOverallBalance(user.getTotalBalance());
        Double capital = 0.0;
        Double returns = 0.0;
        for (Investment investment: investments){
            if (investment.getStatus() == Status.Active){
                capital = capital + investment.getCapital();
                returns = returns + investment.getTotalReturns();
            }
        }
        userList.setOverallCapital(capital);
        userList.setOverallReturns(returns);
        return userList;
    }

    public void updateInvestment(Investment investment) throws FizbizException {
        Investment investment1 = investmentRepository.findById(investment.getId()).get();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        mapper.map(investment, investment1);

        investment1.setModifiedDate(LocalDate.now());
        investmentRepository.save(investment1);
    }
}
