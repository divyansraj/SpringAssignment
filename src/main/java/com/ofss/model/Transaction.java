package com.ofss.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "TRANSACTION")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;
    
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;
    
    @Column(name = "DESTINATION_ACCOUNT_ID", nullable = false)
    private Long destinationAccountId;
    
    @Column(name = "TRANSACTION_DATE", nullable = false)
    private LocalDate transactionDate;
    
    @Column(name = "TRANSACTION_TYPE", nullable = false, length = 20)
    private String transactionType;
    
    @Column(name = "TRANSACTION_AMOUNT", nullable = false, precision = 15, scale = 2)
    private BigDecimal transactionAmount;
    
    // Foreign Key Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", insertable = false, updatable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESTINATION_ACCOUNT_ID", insertable = false, updatable = false)
    private Account destinationAccount;
}