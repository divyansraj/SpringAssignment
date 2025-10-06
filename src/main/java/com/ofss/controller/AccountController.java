package com.ofss.controller;

import com.ofss.dto.AccountDto;
import com.ofss.dto.DepositRequestDto;
import com.ofss.dto.ResponseEntityDto;
import com.ofss.dto.TransferRequestDto;
import com.ofss.dto.WithdrawRequestDto;
import com.ofss.mapper.AccountMapper;
import com.ofss.model.Account;
import com.ofss.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;


    @GetMapping("/accounts")
    public ResponseEntity<ResponseEntityDto> getAllAccounts(){
        List<Account> accounts = accountService.getAllAccounts();
        if(accounts.isEmpty()){
            return ResponseEntity.ok(new ResponseEntityDto("404","No accounts found"));
        } else {
            List<AccountDto> accountDtos = AccountMapper.toDtoList(accounts);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseEntityDto("200","Accounts retrieved successfully", accountDtos));
        }
    }

    @GetMapping("/accounts/id/{accountId}")
    public ResponseEntity<ResponseEntityDto> getAccount(@PathVariable("accountId") Long accountId) {
        Optional<Account> account = accountService.getAccount(accountId);
        if (account.isPresent()) {
            return ResponseEntity.ok(new ResponseEntityDto("200", "Account retrieved successfully", account.get()));
        } else {
            return ResponseEntity.ok(new ResponseEntityDto("404", "No account found"));
        }
    }

    @PostMapping("/accounts")
    public ResponseEntity<ResponseEntityDto> createAccount(@RequestBody Account account) {
        try {
            Account savedAccount = accountService.saveAccount(account);
            return ResponseEntity.status(201)
                    .body(new ResponseEntityDto("201", "Account created successfully", savedAccount));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new ResponseEntityDto("400", "Failed to create account"));
        }
    }

    @PutMapping("/accounts/id/{accountId}")
    public ResponseEntity<ResponseEntityDto> updateAccount(@PathVariable Long accountId, @RequestBody Account account) {
        Optional<Account> existingAccount = accountService.getAccount(accountId);
        if (existingAccount.isPresent()) {
            Account updatedAccount = accountService.updateAccount(accountId,account);
            return ResponseEntity.ok(new ResponseEntityDto("200", "Account updated successfully", updatedAccount));
        } else {
            return ResponseEntity.status(404)
                    .body(new ResponseEntityDto("404", "Account not found"));
        }
    }

    @PatchMapping("/accounts/id/{accountId}")
    public ResponseEntity<ResponseEntityDto> patchAccount(@PathVariable Long accountId, @RequestBody Account accountUpdates) {
        Optional<Account> existingAccount = accountService.getAccount(accountId);
        if (existingAccount.isPresent()) {
            Account updatedAccount = accountService.updateAccountFields(existingAccount.get(), accountUpdates);
            return ResponseEntity.ok(new ResponseEntityDto("200", "Account updated successfully", updatedAccount));
        } else {
            return ResponseEntity.status(404)
                    .body(new ResponseEntityDto("404", "Account not found"));
        }
    }

    @PostMapping("/deposit/accounts/id/{accountId}")
    public ResponseEntity<ResponseEntityDto> deposit(@PathVariable("accountId") Long accountId,
                                                    @RequestBody DepositRequestDto request) {
        try {
            // For demo, assuming customerId is same as account.custId (should be from auth in real app)
            Account account = accountService.deposit(accountId, request, 1L);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseEntityDto("200", "Deposit successful", account));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseEntityDto("400", e.getMessage()));
        }
    }

    @PostMapping("/withdraw/accounts/id/{accountId}")
    public ResponseEntity<ResponseEntityDto> withdraw(@PathVariable("accountId") Long accountId,
                                                     @RequestBody WithdrawRequestDto request) {
        try {
            Account account = accountService.withdraw(accountId, request, 1L);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseEntityDto("200", "Withdraw successful", account));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseEntityDto("400", e.getMessage()));
        }
    }

    @PostMapping("/transfer/fromSourceAccount/{fromAccountId}/toTargetAccount/{toAccountId}")
    public ResponseEntity<ResponseEntityDto> transfer(@PathVariable("fromAccountId") Long fromAccountId,
                                                     @PathVariable("toAccountId") Long toAccountId,
                                                     @RequestBody TransferRequestDto request) {
        try {
            accountService.transfer(fromAccountId, toAccountId, request, 1L);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseEntityDto("200", "Transfer successful"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseEntityDto("400", e.getMessage()));
        }
    }

}
