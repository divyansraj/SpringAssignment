package com.ofss.service;

import java.util.List;

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
}