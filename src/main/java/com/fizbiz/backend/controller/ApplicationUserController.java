package com.fizbiz.backend.controller;

import com.fizbiz.backend.dto.*;
import com.fizbiz.backend.exception.FizbizException;
import com.fizbiz.backend.exception.GeneralExceptionHandler;
import com.fizbiz.backend.models.ApplicationUser;
import com.fizbiz.backend.response.ResponseDetails;
import com.fizbiz.backend.response.ResponseDetailsWithObject;
import com.fizbiz.backend.services.ApplicationUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class ApplicationUserController {

    @Autowired
    ApplicationUserServiceImpl applicationUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ApplicationUser applicationUser, HttpServletRequest httpServletRequest) throws FizbizException {
        ApplicationUser user = applicationUserService.findApplicationUserByEmail(applicationUser.getEmailAddress());
        if (user == null) {
            String url = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getServletPath(), "");
            applicationUserService.registerApplicationUser(applicationUser, url);
        } else {
            throw new FizbizException("User with that email address already exist");
        }
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Your account has been created successfully", HttpStatus.OK.toString());

        return ResponseEntity.status(200).body(responseDetails);
    }

    @PostMapping("/set-pin")
    public ResponseEntity<?> setPin(@Valid @RequestBody SetPinDto setPinDto) throws FizbizException {
        applicationUserService.setPin(setPinDto);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Pin has been set successfully", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }


    @PostMapping ("/request-password-reset")
    public ResponseEntity<?> RequestPasswordReset(@Valid @RequestBody RequestResetPasswordDto resetPasswordDto, HttpServletRequest httpServletRequest) throws  FizbizException {
        String url = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getServletPath(), "");
        applicationUserService.sendResetPasswordToken(resetPasswordDto.getEmailAddress(), url);
        ApplicationUser user = applicationUserService.findApplicationUserByEmail(resetPasswordDto.getEmailAddress());
        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "An email has been sent to you , reset your password",user.getToken(), "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto, HttpServletRequest httpServletRequest) throws FizbizException {
        String url = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getServletPath(), "");
        applicationUserService.resetPassword(resetPasswordDto.getToken(), resetPasswordDto.getPassword(), url);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Password have been changed successfully", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) throws FizbizException {
        applicationUserService.changePassword(changePasswordDto);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "Password have been changed successfully", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@Valid @RequestBody UserVerificationDto email, HttpServletRequest httpServletRequest , WebRequest request) {
        String url = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getServletPath(), "");

        try{
            String response = applicationUserService.verifyEmailToken(email, url);
            ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "User confirmation successful", response, "/api/user/verify");
            return new ResponseEntity<>(responseDetails, HttpStatus.OK);
        }
        catch (FizbizException e){
            return new GeneralExceptionHandler().handleGlobalException(e, request);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) throws FizbizException {
        if (applicationUserService.procurementPartyDoesNotExist(id)){
            throw new FizbizException("User with that id does not exist");
        }
        ApplicationUser user = applicationUserService.findApplicationUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("user/")
//    @PreAuthorize("hasAnyRole('ADMIN, SUPER_ADMIN')")
    public ResponseEntity<?> findAllUsers() {
        List<ApplicationUser> users = applicationUserService.findAllApplicationUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody ApplicationUser applicationUser) throws FizbizException {
        if (applicationUser.getId() == null) {
            throw new FizbizException("User id cannot be null");
        }
        if (applicationUserService.procurementPartyDoesNotExist(applicationUser.getId())){
            throw new FizbizException("User with that id does not exist");
        }
        applicationUserService.updateApplicationUser(applicationUser);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "User account updated successfully", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }


    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) throws FizbizException {
        if (applicationUserService.procurementPartyDoesNotExist(id)){
            throw new FizbizException("User with that id does not exist");
        }
        applicationUserService.deactivateApplicationUserById(id);
        ResponseDetails responseDetails = new ResponseDetails(LocalDateTime.now(), "User account deactivated successfully", "success");
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }


}

