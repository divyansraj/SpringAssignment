package com.ofss.service;

import com.ofss.dto.DepositRequestDto;
import com.ofss.dto.TransferRequestDto;
import com.ofss.dto.WithdrawRequestDto;
import com.ofss.model.Account;
import com.ofss.model.Transaction;
import com.ofss.repository.AccountRepository;
import com.ofss.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public List<Account> getAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    public Optional<Account> getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long accountId, Account account) {
        account.setAccountNumber(accountId);
        account.setAccountCreationDate(accountRepository.findById(accountId).get().getAccountCreationDate());
        account.setCustId(accountRepository.findById(accountId).get().getCustId());
        account.setBankId(accountRepository.findById(accountId).get().getBankId());
        return accountRepository.save(account);
    }

    public Account updateAccountFields(Account existingAccount, Account accountUpdates) {
        if (accountUpdates.getAccountType() != null) {
            existingAccount.setAccountType(accountUpdates.getAccountType());
        }
        if (accountUpdates.getBalance() != null) {
            existingAccount.setBalance(accountUpdates.getBalance());
        }
        if (accountUpdates.getIfscCode() != null) {
            existingAccount.setIfscCode(accountUpdates.getIfscCode());
        }
        return accountRepository.save(existingAccount);
    }

    @Transactional
    public Account deposit(Long accountId, DepositRequestDto request, Long customerId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setBalance(account.getBalance().add(request.getDepositAmount()));
        accountRepository.save(account);
        Transaction transaction = new Transaction();
        transaction.setCustomerId(account.getCustomer().getCustId());
        transaction.setDestinationAccountId(accountId);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType("DEPOSIT");
        transaction.setTransactionAmount(request.getDepositAmount());
        transactionRepository.save(transaction);
        return account;
    }

    @Transactional
    public Account withdraw(Long accountId, WithdrawRequestDto request, Long customerId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (account.getBalance().compareTo(request.getWithdrawAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(request.getWithdrawAmount()));
        accountRepository.save(account);
        Transaction transaction = new Transaction();
        transaction.setCustomerId(account.getCustomer().getCustId());
        transaction.setDestinationAccountId(accountId);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType("WITHDRAW");
        transaction.setTransactionAmount(request.getWithdrawAmount());
        transactionRepository.save(transaction);
        return account;
    }

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, TransferRequestDto request, Long customerId) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));
        if (fromAccount.getBalance().compareTo(request.getTransferAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getTransferAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getTransferAmount()));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        Transaction transaction = new Transaction();
        transaction.setCustomerId(customerId);
        transaction.setDestinationAccountId(toAccountId);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType("TRANSFER");
        transaction.setTransactionAmount(request.getTransferAmount());
        transactionRepository.save(transaction);
    }
}