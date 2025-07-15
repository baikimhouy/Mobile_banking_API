package org.example.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KYC")
public class KYC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private Integer nationalCardId;

    @Column(nullable = false)
    private Integer phoneNumber;

    @Column(nullable = false)
    private String segment;

    @Column(nullable = false)
    private boolean verified = false;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
