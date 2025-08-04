package org.example.mobilebankingapi.init;

import jakarta.annotation.PostConstruct;
import lombok.*;
import org.example.mobilebankingapi.domain.Role;
import org.example.mobilebankingapi.domain.User;
import org.example.mobilebankingapi.repository.RoleRepository;
import org.example.mobilebankingapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityInit {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {

        Role defaultRole = new Role();
        defaultRole.setRole("USER");
        Role admin = new Role();
        admin.setRole("ADMIN");
        Role staff = new Role();
        staff.setRole("STAFF");
        Role customer = new Role();
        customer.setRole("CUSTOMER");

        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(defaultRole,admin,staff,customer));
        }

        if (userRepository.count() == 0) {
            User userAdmin = new User();
            userAdmin.setUsername("admin");
            userAdmin.setPassword(passwordEncoder.encode("admin"));
            userAdmin.setEnabled(true);
            userAdmin.setRole(List.of(defaultRole, admin));

            User userStaff = new User();
            userStaff.setUsername("staff");
            userStaff.setPassword(passwordEncoder.encode("staff"));
            userStaff.setEnabled(true);
            userStaff.setRole(List.of(defaultRole, staff));

            User userCustomer = new User();
            userCustomer.setUsername("customer");
            userCustomer.setPassword(passwordEncoder.encode("customer"));
            userCustomer.setEnabled(true);
            userCustomer.setRole(List.of(defaultRole, customer));

            userRepository.saveAll(List.of(userAdmin,userStaff,userCustomer));
        }

}

}
