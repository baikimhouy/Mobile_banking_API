package org.example.mobilebankingapi.mapper;

import org.example.mobilebankingapi.DTO.CreateCustomerRequest;
import org.example.mobilebankingapi.DTO.CustomerResponse;
import org.example.mobilebankingapi.DTO.UpdateCustomerRequest;
import org.example.mobilebankingapi.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartiallyUpdate(UpdateCustomerRequest request, @MappingTarget Customer customer);

    @Mapping(target = "nationalCardId", source = "kyc.nationalCardId")
    @Mapping(target = "segment", source = "kyc.segment")
    @Mapping(target = "verified", source = "kyc.verified")
    @Mapping(target = "remarks", source = "remarks") // Optional if names match
    CustomerResponse fromCustomer(Customer customer);

    Customer toCustomer(CreateCustomerRequest request);
}


