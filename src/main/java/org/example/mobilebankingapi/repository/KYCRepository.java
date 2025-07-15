package org.example.mobilebankingapi.repository;

import org.example.mobilebankingapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYC, Integer> {
    Optional<KYC> findByNationalCardId(Integer nationalCardId);

}
