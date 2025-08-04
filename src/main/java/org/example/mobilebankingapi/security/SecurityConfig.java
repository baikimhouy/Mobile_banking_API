package org.example.mobilebankingapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
//    private final PasswordEncoder passwordEncoder;
//    private final UserDetailsService userDetailsService;
//
//    private final String ROLE_ADMIN = "ADMIN";
//    private final String ROLE_CUSTOMER = "CUSTOMER";
//    private final String ROLE_STAFF = "STAFF";
//
////    @Bean
////    public InMemoryUserDetailsManager configureUser(){
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        UserDetails user = User.builder()
////                            .username("admin")
////                            .password(passwordEncoder.encode("admin"))
////                            .roles("ADMIN").build();
////        manager.createUser(user);
////
////        UserDetails staff = User.builder()
////                            .username("staff")
////                            .password(passwordEncoder.encode("staff"))
////                            .roles("STAFF").build();
////
////        manager.createUser(staff);
////
////        UserDetails customer = User.builder()
////                            .username("customer")
////                            .password(passwordEncoder.encode("customer"))
////                            .roles("CUSTOMER").build();
////
////        manager.createUser(customer);
////
////        return manager;
////    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//
//        return daoAuthenticationProvider;
//    }
//
//    @Bean
//    //is like a wall of security securityFilterChain
//    public SecurityFilterChain configureApiSecurity(HttpSecurity http) throws Exception {
//
//        //TODO
//        //1 make all endpoint secure
//        http.authorizeHttpRequests(endpoints -> endpoints
//                        .requestMatchers(HttpMethod.GET,"/api/v1/customer/**")
//                        .hasAnyRole(ROLE_CUSTOMER, ROLE_ADMIN, ROLE_STAFF)
//                        .requestMatchers(HttpMethod.POST,"/api/v1/customers/**")
//                        .hasAnyRole(ROLE_CUSTOMER,ROLE_ADMIN)
//                        .requestMatchers(HttpMethod.DELETE,"/api/v1/accounts/**")
//                        .hasRole(ROLE_ADMIN)
//
//                        .anyRequest()
//                        .authenticated()
////                .permitALL() is mean not include security
//        );
//        //set security mechanism = HTTP basic authentication
//        http.httpBasic(Customizer.withDefaults());
//
//        //Disable form login
//        http.formLogin(form -> form.disable());
//
//        http.csrf(token -> token.disable());
//
//        //make stateless api
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//
//        return http.build();
  //  }
}
