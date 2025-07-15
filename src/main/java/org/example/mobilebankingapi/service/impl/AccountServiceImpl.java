package org.example.mobilebankingapi.service.impl;


import org.example.mobilebankingapi.DTO.AccountRequest;
import org.example.mobilebankingapi.DTO.AccountResponse;
import org.example.mobilebankingapi.DTO.AccountUpdateRequest;
import org.example.mobilebankingapi.domain.*;
import org.example.mobilebankingapi.mapper.*;
import org.example.mobilebankingapi.repository.*;
import org.example.mobilebankingapi.service.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.*;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerRepository customerRepository,
                              AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.accountMapper = accountMapper;
    }
    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Account account = accountMapper.toAccount(request);
        account.setCustomer(customer);
        account.setDeleted(false);
        account.setActive(true);

        String segment = customer.getKyc().getSegment();
        BigDecimal overLimit = switch (segment) {
            case "Gold" -> BigDecimal.valueOf(50000);
            case "Silver" -> BigDecimal.valueOf(10000);
            default -> BigDecimal.valueOf(5000);
        };
        account.setOverLimit(overLimit);

        Account saved = accountRepository.save(account);
        return accountMapper.toAccountResponse(saved);
    }



    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findAllAccounts() {
        return accountRepository.findAll().stream()
                .filter(account -> !account.isDeleted())
                .map(accountMapper::toAccountResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse findAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account is disabled");
        }

        return accountMapper.toAccountResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findAccountsByCustomer(Integer customerId) {
        return accountRepository.findByCustomerIdAndDeletedFalse(customerId)
                .stream()
                .map(accountMapper::toAccountResponse)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountRepository.delete(account);
    }

    @Override
    public AccountResponse updateAccountByAccountNumber(String accountNumber, AccountUpdateRequest request) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update disabled account");
        }

        accountMapper.updateAccountFromRequest(request, account);
        Account updatedAccount = accountRepository.save(account);
        return accountMapper.toAccountResponse(updatedAccount);
    }

    @Override
    public AccountResponse disableAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account already disabled");
        }

        account.setDeleted(true);
        Account disabledAccount = accountRepository.save(account);
        return accountMapper.toAccountResponse(disabledAccount);
    }
}