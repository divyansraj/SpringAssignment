package com.ofss.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "ACCOUNT")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;
    
    @Column(name = "ACCOUNT_CREATION_DATE",updatable = false)
    private LocalDate accountCreationDate = LocalDate.now(); // Default to current date
    
    @Column(name = "ACCOUNT_TYPE", length = 20)
    private String accountType;
    
    @Column(name = "BALANCE", precision = 15, scale = 2)
    private BigDecimal balance;  // Using BigDecimal for precise decimal calculations
    
    @Column(name = "IFSC_CODE", length = 20)
    private String ifscCode;
    
    @Column(name = "CUST_ID", nullable = false)
    private Long custId;
    
    @Column(name = "BANK_ID", nullable = false)
    private Long bankId;
    
    // Foreign Key Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUST_ID", insertable = false, updatable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_ID", insertable = false, updatable = false)
    private Bank bank;
}