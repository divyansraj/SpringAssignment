package com.ofss.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ofss.model.Bank;
import com.ofss.repository.BankRepository;

@Service
@RequiredArgsConstructor
public class BankServices {
    private final BankRepository bankRepository;

    public List<Bank> getBanks() {
        return (List<Bank>) bankRepository.findAll();
    }

    public Optional<Bank> getBankById(Long bankId) {
        return bankRepository.findById(bankId);
    }

    public Bank saveBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public Bank updateBank(Long bankId, Bank bank) {
        bank.setBankId(bankId);
        return bankRepository.save(bank);
    }

    public Bank patchBank(Long bankId, Bank bankUpdates) {
        Optional<Bank> existingBankOpt = bankRepository.findById(bankId);
        if (existingBankOpt.isPresent()) {
            Bank existingBank = existingBankOpt.get();

            if (bankUpdates.getBankName() != null) {
                existingBank.setBankName(bankUpdates.getBankName());
            }
            if (bankUpdates.getBranchName() != null) {
                existingBank.setBranchName(bankUpdates.getBranchName());
            }
            if (bankUpdates.getIfscCode() != null) {
                existingBank.setIfscCode(bankUpdates.getIfscCode());
            }

            return bankRepository.save(existingBank);
        }
        return null;
    }
}