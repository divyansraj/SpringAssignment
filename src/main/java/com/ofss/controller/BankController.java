package com.ofss.controller;

import java.util.List;

import com.ofss.dto.ResponseEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ofss.model.Bank;
import com.ofss.service.BankServices;

@RestController
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

//    Bank related (5 APIs)
//    GET /banks
//    GET /banks/id/{bankId}
//    POST /banks
//    PUT /banks/id/{bankId}
//    PATCH /banks/id/{bankId}

}