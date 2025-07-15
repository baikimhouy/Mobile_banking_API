package org.example.mobilebankingapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.mobilebankingapi.DTO.CreateCustomerRequest;
import org.example.mobilebankingapi.DTO.CustomerResponse;
import org.example.mobilebankingapi.DTO.UpdateCustomerRequest;
import org.example.mobilebankingapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{phoneNumber}")
    public void disableByPhoneNumber(@PathVariable String phoneNumber) {
        customerService.disableByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }
    @GetMapping("/{phoneNumber}")
    public CustomerResponse getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        return customerService.findCustomerByphoneNumber(phoneNumber);
    }
    @PatchMapping("/{phoneNumber}")
    public CustomerResponse updateCustomer(@PathVariable String phoneNumber,@Valid @RequestBody UpdateCustomerRequest request) {
        return customerService.updateCustomer(phoneNumber,request);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{phoneNumber}")
    public void deleteCustomer(@PathVariable String phoneNumber) {
        customerService.deleteCustomer(phoneNumber);
    }

}
