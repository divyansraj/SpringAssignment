package com.ofss.controller;

import java.util.List;
import java.util.Optional;

import com.ofss.dto.ResponseEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ofss.model.Bank;
import com.ofss.service.BankServices;

@RestController
@RequestMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BankController {

	private final BankServices bankService;

    @GetMapping("/banks")
    public ResponseEntity<ResponseEntityDto> getAllBanks(){
        List<Bank> banks = bankService.getBanks();
        if(banks.isEmpty()){
            return ResponseEntity.ok(new ResponseEntityDto("404","No banks found"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseEntityDto("200","Banks retrieved successfully", banks));
        }
    }

    @GetMapping("/banks/id/{bankId}")
    public ResponseEntity<ResponseEntityDto> getBankById(@PathVariable Long bankId) {
        Optional<Bank> bank = bankService.getBankById(bankId);
        if (bank.isPresent()) {
            return ResponseEntity.ok(new ResponseEntityDto("200", "Bank retrieved successfully", bank.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseEntityDto("404", "Bank not found"));
        }
    }

    @PostMapping("/banks")
    public ResponseEntity<ResponseEntityDto> createBank(@RequestBody Bank bank) {
        try {
            Bank savedBank = bankService.saveBank(bank);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseEntityDto("201", "Bank created successfully", savedBank));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseEntityDto("400", "Failed to create bank"));
        }
    }

    @PutMapping("/banks/id/{bankId}")
    public ResponseEntity<ResponseEntityDto> updateBank(@PathVariable Long bankId, @RequestBody Bank bank) {
        Optional<Bank> existingBank = bankService.getBankById(bankId);
        if (existingBank.isPresent()) {
            Bank updatedBank = bankService.updateBank(bankId, bank);
            return ResponseEntity.ok(new ResponseEntityDto("200", "Bank updated successfully", updatedBank));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseEntityDto("404", "Bank not found"));
        }
    }

    @PatchMapping("/banks/id/{bankId}")
    public ResponseEntity<ResponseEntityDto> patchBank(@PathVariable Long bankId, @RequestBody Bank bankUpdates) {
        Bank updatedBank = bankService.patchBank(bankId, bankUpdates);
        if (updatedBank != null) {
            return ResponseEntity.ok(new ResponseEntityDto("200", "Bank updated successfully", updatedBank));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseEntityDto("404", "Bank not found"));
        }
    }

}