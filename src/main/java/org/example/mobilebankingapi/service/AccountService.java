package org.example.mobilebankingapi.service;

import org.example.mobilebankingapi.DTO.AccountRequest;
import org.example.mobilebankingapi.DTO.AccountResponse;
import org.example.mobilebankingapi.DTO.AccountUpdateRequest;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(AccountRequest request);

    List<AccountResponse> findAllAccounts();

    AccountResponse findAccountByAccountNumber(String accountNumber);

    List<AccountResponse> findAccountsByCustomer(Integer customerId);

    void deleteAccountByAccountNumber(String accountNumber);

    AccountResponse updateAccountByAccountNumber(String accountNumber, AccountUpdateRequest request);

    AccountResponse disableAccount(String accountNumber);


}