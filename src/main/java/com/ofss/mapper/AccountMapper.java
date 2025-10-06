package com.ofss.mapper;

import com.ofss.dto.AccountDto;
import com.ofss.model.Account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountDto toDto(Account account) {
        if (account == null) return null;
        AccountDto dto = new AccountDto();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountCreationDate(account.getAccountCreationDate());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setIfscCode(account.getIfscCode());
        dto.setCustId(account.getCustId());
        dto.setBankId(account.getBankId());
        // Optionally map customer or bank details here as needed
        return dto;
    }

    public static List<AccountDto> toDtoList(List<Account> accounts) {
        return accounts.stream().map(AccountMapper::toDto).collect(Collectors.toList());
    }
}