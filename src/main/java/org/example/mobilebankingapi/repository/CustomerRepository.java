package org.example.mobilebankingapi.repository;

import org.example.mobilebankingapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    //JPQL
    @Query(value = """
    SELECT EXISTS (SELECT c FROM Customer c WHERE c.phoneNumber = ?1)
    """)
    boolean isExitsByPhoneNumber(String phoneNumber);

    Optional<Customer> findByPhoneNumber(String phoneNumber);


    @Modifying
    @Query(value = """
    UPDATE Customer c SET c.isDeleted =TRUE WHERE c.phoneNumber = :phoneNumber
    """)
    boolean exitByPhoneNumber(String phoneNumber);
}
