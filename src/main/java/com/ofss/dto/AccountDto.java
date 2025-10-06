package com.ofss.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

// You can add @AllArgsConstructor, @NoArgsConstructor, @Builder if desired
@Data
public class AccountDto {
    private Long accountNumber;
    private LocalDate accountCreationDate;
    private String accountType;
    private BigDecimal balance;
    private String ifscCode;
    private Long custId;
    private Long bankId;
    // Optional: You can add customer info fields if needed, or nest a CustomerDto
}