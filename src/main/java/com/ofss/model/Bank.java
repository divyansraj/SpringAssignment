package com.ofss.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "BANK")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bank {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_ID")
    private Long bankId;  // Changed to Long to match NUMBER in Oracle
    
    @Column(name = "BANK_NAME", nullable = false, length = 200)
    private String bankName;
    
    @Column(name = "BRANCH_NAME", length = 200)
    private String branchName;
    
    @Column(name = "IFSC_CODE", nullable = false, unique = true, length = 20)
    private String ifscCode;

}