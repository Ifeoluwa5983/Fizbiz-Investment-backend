package com.fizbiz.backend.services;

import com.fizbiz.backend.models.ApplicationUser;
import com.fizbiz.backend.models.Investment;

import java.util.List;

public interface AdminService {

    List<ApplicationUser> findAllUser();

    List<Investment> findAllInvestment();
}
