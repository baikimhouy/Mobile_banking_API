package org.example.mobilebankingapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobilebankingapi.DTO.CreateCustomerRequest;
import org.example.mobilebankingapi.DTO.CustomerResponse;
import org.example.mobilebankingapi.DTO.UpdateCustomerRequest;
import org.example.mobilebankingapi.domain.Customer;
import org.example.mobilebankingapi.domain.KYC;
import org.example.mobilebankingapi.mapper.CustomerMapper;
import org.example.mobilebankingapi.repository.CustomerRepository;
import org.example.mobilebankingapi.repository.KYCRepository;
import org.example.mobilebankingapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final KYCRepository kYCRepository;

    @Override
    public CustomerResponse findCustomerByphoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        return customerMapper.fromCustomer(customer);
    }


    @Transactional
    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Customer customer = customerMapper.toCustomer(request);
        customer.setDeleted(false);
        Customer savedCustomer = customerRepository.save(customer);

        KYC kyc = new KYC();
        kyc.setCustomer(savedCustomer);
        kyc.setNationalCardId(request.nationalCardId());
        kyc.setPhoneNumber(Integer.parseInt(request.phoneNumber()));
        kyc.setSegment(request.segment());
        kyc.setVerified(false);
        kYCRepository.save(kyc);

        savedCustomer.setKyc(kyc);

        return customerMapper.fromCustomer(savedCustomer);
    }



    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .filter(c -> !c.isDeleted())
                .map(customerMapper::fromCustomer)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(String phoneNumber, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        customerMapper.toCustomerPartiallyUpdate(request, customer);
        customer = customerRepository.save(customer);
        return customerMapper.fromCustomer(customer);
    }

    @Override
    public void deleteCustomer(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customerRepository.delete(customer);
    }

    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {
        if(!customerRepository.exitByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        customerRepository.isExitsByPhoneNumber(phoneNumber);
    }

    @Override
    public void verifyKYC(Integer nationalCardId) {
        KYC kyc = kYCRepository.findByNationalCardId(nationalCardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "KYC not found"));
        kyc.setVerified(true);
        kYCRepository.save(kyc);
    }

}
