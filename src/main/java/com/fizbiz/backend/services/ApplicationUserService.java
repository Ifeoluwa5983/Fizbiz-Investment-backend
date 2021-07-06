package com.fizbiz.backend.services;

import com.fizbiz.backend.dto.SetPinDto;
import com.fizbiz.backend.dto.UserVerificationDto;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.models.ApplicationUser;

import java.util.List;

public interface ApplicationUserService {

    void registerApplicationUser(ApplicationUser applicationUser, String url) throws FizbizException;

    String verifyEmailToken(UserVerificationDto email, String url) throws FizbizException;

    void sendResetPasswordToken(String email, String url) throws FizbizException;

    void resetPassword(String resetPasswordToken, String newPassword, String url) throws FizbizException;

    ApplicationUser findApplicationUserByEmail(String email);

    ApplicationUser findApplicationUserById(String id);

    void deactivateApplicationUserById(String id) throws FizbizException;

    List<ApplicationUser> findAllApplicationUsers();

    void updateApplicationUser(ApplicationUser applicationUser) throws FizbizException;

    void setPin(SetPinDto setPinDto) throws FizbizException;
}
