package org.example.mobilebankingapi.mapper;

import org.example.mobilebankingapi.DTO.AccountRequest;
import org.example.mobilebankingapi.DTO.AccountResponse;
import org.example.mobilebankingapi.DTO.AccountUpdateRequest;
import org.example.mobilebankingapi.domain.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "deleted", constant = "false")
        @Mapping(target = "customer", ignore = true) // Set manually in service
        @Mapping(target = "overLimit", ignore = true) // Set manually based on segment
        Account toAccount(AccountRequest request);

        @Mapping(source = "customer.id", target = "customerId")
        @Mapping(source = "customer.fullName", target = "customerName")
        @Mapping(target = "active", expression = "java(!account.isDeleted())")
        AccountResponse toAccountResponse(Account account);

        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        void updateAccountFromRequest(AccountUpdateRequest request, @MappingTarget Account account);
}
