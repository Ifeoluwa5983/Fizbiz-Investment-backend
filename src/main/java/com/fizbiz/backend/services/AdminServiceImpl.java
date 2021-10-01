package com.fizbiz.backend.services;

import com.fizbiz.backend.models.ApplicationUser;
import com.fizbiz.backend.models.Investment;
import com.fizbiz.backend.repositories.ApplicationUserRepository;
import com.fizbiz.backend.repositories.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    InvestmentRepository investmentRepository;

    @Override
    public List<ApplicationUser> findAllUser() {
        return applicationUserRepository.findAll();
    }

    @Override
    public List<Investment> findAllInvestment() {
        return investmentRepository.findAll();
    }
}
