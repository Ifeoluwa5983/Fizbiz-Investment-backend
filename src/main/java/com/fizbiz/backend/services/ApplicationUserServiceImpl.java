package com.fizbiz.backend.services;

import com.fizbiz.backend.dto.ChangePasswordDto;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.models.ApplicationUser;
import com.fizbiz.backend.models.Role;
import com.fizbiz.backend.notification.EmailSenderServiceImpl;
import com.fizbiz.backend.repositories.ApplicationUserRepository;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class ApplicationUserServiceImpl implements ApplicationUserService{

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    Random random = new Random();

    @Autowired
    TemplateEngine templateEngine;

    public boolean procurementPartyDoesNotExist(String id) {
        return !applicationUserRepository.existsById(id);
    }

    public Boolean procurementPartyDoesntExist(String email){
        return !applicationUserRepository.existsByEmailAddress(email);
    }

    @Override
    public void registerApplicationUser(ApplicationUser applicationUser, String url) throws FizbizException {
        ApplicationUser user = applicationUserRepository.findByToken(applicationUser.getToken());
        if (user == null) {
            throw new FizbizException("Invalid request. Please try again later");
        }
        user.setRole(Role.USER);
        user.setModifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        user.setPassword(encryptPassword(applicationUser.getPassword()));
        user.setRegisteredDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        user.setIsActive(true);
        user.setGender(applicationUser.getGender());
        user.setDateOfBirth(applicationUser.getDateOfBirth());
        user.setPhoneNumber(applicationUser.getPhoneNumber());
        user.setHomeAddress(applicationUser.getHomeAddress());
        user.setTotalBalance(0.0);
        user.setOverallCapital(0.0);
        user.setFirstName(applicationUser.getFirstName());
        user.setFirstName(applicationUser.getLastName());
        applicationUserRepository.save(user);
        sendConfirmationEmail(user, url);
    }
    @Override
    public String verifyEmailToken(String email, String url) throws FizbizException {

        String verificationToken = String.format("%04d", random.nextInt(10000));
        if (applicationUserRepository.findByEmailAddress(email) != null) {
            throw new FizbizException("User already exist");
        }

        ApplicationUser user = new ApplicationUser();
        user.setToken(verificationToken);
        user.setEmailAddress(email);
        sendVerificationEmail(user, verificationToken, url);
        applicationUserRepository.save(user);
        return  user.getToken();
    }

    public void changePassword(ChangePasswordDto changePasswordDto) throws FizbizException {
        ApplicationUser applicationUser = findApplicationUserById(changePasswordDto.getUserId());
        if (applicationUser == null){
            throw new FizbizException("User does not exist");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (!bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), applicationUser.getPassword())){
            throw new FizbizException("Incorrect password");
        }
        applicationUser.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
    }

    @Override
    public void sendResetPasswordToken(String email, String url) throws FizbizException {
        ApplicationUser applicationUser = applicationUserRepository.findByEmailAddress(email);
        if (applicationUser == null) {
            throw new FizbizException("User with " + email + "does not exist");
        } else {
            String resetPasswordToken = RandomString.make(64);
            applicationUser.setToken(resetPasswordToken);
            applicationUserRepository.save(applicationUser);

            sendResetPasswordEmail(applicationUser, url);
        }
    }

    private void sendResetPasswordEmail(ApplicationUser applicationUser, String url) throws FizbizException {
        String toAddress = applicationUser.getEmailAddress();
        String fromAddress = "iclasschima@gmail.com";
        String senderName = "onHover";
        String subject = "Reset your password";
        String verifyURL = url + "/verify?token=" + applicationUser.getToken();

        Context context = new Context();
        context.setVariable("name", applicationUser.getFirstName());
        context.setVariable("link", verifyURL);

        String content = templateEngine.process("resetPassword", context);

        new EmailSenderServiceImpl().sendNotification(fromAddress, senderName, toAddress, subject, verifyURL, content);
    }

    @Override
    public void resetPassword(String resetPasswordToken, String newPassword, String url) throws FizbizException {
        ApplicationUser applicationUser = applicationUserRepository.findByToken(resetPasswordToken);

        if (applicationUser == null) {
            throw new FizbizException("User does not exist");
        } else {
            applicationUser.setToken(null);
            applicationUser.setPassword(newPassword);
            sendConfirmResetPasswordEmail(applicationUser, url);
            applicationUserRepository.save(applicationUser);
        }
    }

    private void sendConfirmResetPasswordEmail(ApplicationUser applicationUser, String url) throws FizbizException {
        String toAddress = applicationUser.getEmailAddress();
        String fromAddress = "o.ifeoluwah@gmail.com";
        String senderName = "Fizbiz";
        String subject = "Welcome to Fizbiz Investment Platform";
        String verifyURL = url + "/verify?token=" + applicationUser.getToken();

        Context context = new Context();
        context.setVariable("name", applicationUser.getFirstName());
        context.setVariable("link", verifyURL);

        String content = templateEngine.process("resetPasswordConfirmation", context);

        new EmailSenderServiceImpl().sendNotification(fromAddress, senderName, toAddress, subject, verifyURL, content);
    }

    @Override
    public ApplicationUser findApplicationUserByEmail(String email) {
        return applicationUserRepository.findByEmailAddress(email);
    }

    @Override
    public ApplicationUser findApplicationUserById(String id) {
        return applicationUserRepository.findById(id).orElse(null);
    }

    @Override
    public void deactivateApplicationUserById(String id) throws FizbizException {
        ApplicationUser procurementParty = applicationUserRepository.findById(id).orElse(null);
        assert procurementParty != null;
        if (!procurementParty.getIsActive()){
            throw new FizbizException("This application user has already been deactivated");
        }
        applicationUserRepository.deactivateById(id);
    }


    @Override
    public List<ApplicationUser> findAllApplicationUsers() {
        return applicationUserRepository.findAll();
    }


    @Override
    public void updateApplicationUser(ApplicationUser updateProcurementPartyDto) throws FizbizException {
        ApplicationUser existingUser = applicationUserRepository.findById(updateProcurementPartyDto.getId()).orElse(new ApplicationUser());

        if (!existingUser.getIsActive()){
            throw new FizbizException("You cannot update a user that has been deactivated");
        }

        if (updateProcurementPartyDto.getFirstName() != null) {
            existingUser.setFirstName(updateProcurementPartyDto.getFirstName());
        }
        if (updateProcurementPartyDto.getLastName() != null) {
            existingUser.setLastName(updateProcurementPartyDto.getLastName());
        }
        if (updateProcurementPartyDto.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updateProcurementPartyDto.getPhoneNumber());
        }
        if (updateProcurementPartyDto.getGender() != null) {
            existingUser.setGender(updateProcurementPartyDto.getGender());
        }

        existingUser.setModifiedDate(LocalDateTime.now().toString());
        applicationUserRepository.save(existingUser);
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    private void sendVerificationEmail(ApplicationUser applicationUser, String code, String siteURL) throws FizbizException {

        String toAddress = applicationUser.getEmailAddress();
        String fromAddress = "o.ifeoluwah@gmail.com@gmail.com";
        String senderName = "Fizbiz";
        String subject = "VERIFY YOUR EMAIL ADDRESS";
        String verifyURL = siteURL + "/verify?token=" + applicationUser.getToken();

        Context context = new Context();
        context.setVariable("name", applicationUser.getFirstName());
        context.setVariable("code", code);


        String content = templateEngine.process("welcome", context);


        new EmailSenderServiceImpl().sendNotification(fromAddress, senderName, toAddress, subject, verifyURL, content);
    }

    private void sendConfirmationEmail(ApplicationUser applicationUser, String siteURL) throws FizbizException {
        String toAddress = applicationUser.getEmailAddress();
        String fromAddress = "o.ifeoluwah@gmail.com";
        String senderName = "Fizbiz";
        String subject = "Welcome to Fikziz Investment Platform";
        String verifyURL = siteURL + "/verify?token=" + applicationUser.getToken();

        Context context = new Context();
        context.setVariable("name", applicationUser.getFirstName());
        context.setVariable("link", verifyURL);

        String content = templateEngine.process("confirmationEmail", context);

        new EmailSenderServiceImpl().sendNotification(fromAddress, senderName, toAddress, subject, verifyURL, content);
    }

}

