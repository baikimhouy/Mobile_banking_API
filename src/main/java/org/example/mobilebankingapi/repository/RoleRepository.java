package org.example.mobilebankingapi.repository;

import org.example.mobilebankingapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
