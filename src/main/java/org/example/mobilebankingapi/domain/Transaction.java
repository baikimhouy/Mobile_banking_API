package org.example.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne //many transaction can has same transaction type
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @ManyToOne //many transaxction has one sender acc
    @JoinColumn(name = "sender_account_id", nullable = false)
    private Account sender;

    @ManyToOne //many transaction has one resiver
    @JoinColumn(name = "receiver_account_id", nullable = true)
    private Account receiver;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
