package com.backend.ecom.controllers;

import com.backend.ecom.payload.request.ChangeAvaRequest;
import com.backend.ecom.payload.request.ChangePasswordRequest;
import com.backend.ecom.payload.request.ResetPasswordRequest;
import com.backend.ecom.payload.request.UpdatePasswordAfterResetRequest;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    @PostMapping("/setAva")
    public ResponseEntity<ResponseObject> setUserAva(@Valid @RequestBody ChangeAvaRequest ava) {
        return accountService.setUserAva(ava);
    }

    @PostMapping("/password/forget")
    public ResponseEntity<HttpStatus> resetUserPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return accountService.resetUserPassword(resetPasswordRequest);
    }

    @PostMapping("/password/forget/codeVerify")
    public ResponseEntity<HttpStatus> checkResetUserPasswordCode(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return accountService.checkResetUserPasswordCode(resetPasswordRequest);
    }

    @PostMapping("/password/forget/changePassword")
    public ResponseEntity<ResponseObject> updateNewPassword(@Valid @RequestBody UpdatePasswordAfterResetRequest updatePasswordAfterResetRequest) {
        return accountService.updateNewPassword(updatePasswordAfterResetRequest);
    }

    @PostMapping("/update/password")
    public ResponseEntity<ResponseObject> updateUserCurrentPassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return accountService.updateUserCurrentPassword(changePasswordRequest);
    }
}
