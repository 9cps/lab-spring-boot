package com.cps.pwm.controller;

import com.cps.pwm.business.AccountBusiness;
import com.cps.pwm.dto.request.AccountRequest;
import com.cps.pwm.dto.response.AccountResponse;
import com.cps.pwm.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountBusiness accountBusiness;
    private final AccountService accountService;

    public AccountController(AccountBusiness accountBusiness, AccountService accountService) {
        this.accountBusiness = accountBusiness;
        this.accountService = accountService;
    }

    // Create Account
    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountRequest request) {
        boolean success = accountBusiness.createAccount(request.getUserId(), request.getCurrency());
        if (success) {
            return ResponseEntity.ok("Account created successfully");
        } else {
            return ResponseEntity.internalServerError().body("Failed to create account");
        }
    }

    // Get List Account By UserId
    @GetMapping("/{id}")
    public AccountResponse getAllAccountByUserId(@PathVariable Long id) {
        return accountBusiness.findAllAccountByUserId(id);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
