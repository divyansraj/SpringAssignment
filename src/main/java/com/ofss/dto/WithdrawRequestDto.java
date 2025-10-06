package com.ofss.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WithdrawRequestDto {
    private BigDecimal withdrawAmount;
}

