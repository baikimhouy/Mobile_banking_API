package org.example.mobilebankingapi.controller;


import org.example.mobilebankingapi.DTO.*;
import org.example.mobilebankingapi.service.AccountService;
import org.example.mobilebankingapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @PutMapping("/kyc/verify/{nationalCardId}")
    public ResponseEntity<String> verifyKYC(@PathVariable Integer nationalCardId) {
        customerService.verifyKYC(nationalCardId);
        return ResponseEntity.ok("KYC verified successfully");
    }


    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> responses = accountService.findAllAccounts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccountByNumber(
            @PathVariable String accountNumber) {
        AccountResponse response = accountService.findAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByCustomer(
            @PathVariable Integer customerId) {
        List<AccountResponse> responses = accountService.findAccountsByCustomer(customerId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable String accountNumber) {
        accountService.deleteAccountByAccountNumber(accountNumber);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable String accountNumber,
            @Valid @RequestBody AccountUpdateRequest request) {
        AccountResponse response = accountService.updateAccountByAccountNumber(accountNumber, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{accountNumber}/disable")
    public ResponseEntity<AccountResponse> disableAccount(
            @PathVariable String accountNumber) {
        AccountResponse response = accountService.disableAccount(accountNumber);
        return ResponseEntity.ok(response);
    }

}