package org.example.mobilebankingapi.service;

import org.example.mobilebankingapi.DTO.CreateCustomerRequest;
import org.example.mobilebankingapi.DTO.CustomerResponse;
import org.example.mobilebankingapi.DTO.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {
    CustomerResponse findCustomerByphoneNumber(String phoneNumber);

    CustomerResponse createCustomer(CreateCustomerRequest request);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse updateCustomer(String phoneNumber, UpdateCustomerRequest request);

    void deleteCustomer(String phoneNumber);

    void disableByPhoneNumber(String phoneNumber);

    void verifyKYC(Integer nationalCardId);



}
