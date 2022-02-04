package com.kinandcarta.transactionsapi.service;

import com.kinandcarta.transactionsapi.domain.entity.Account;
import com.kinandcarta.transactionsapi.domain.response.AccountTransactionResponse;
import com.kinandcarta.transactionsapi.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionsService {
    private final AccountRepository accountRepository;

    public TransactionsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountTransactionResponse getTransactions(long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Account accountEntity = optionalAccount.get();
        return AccountTransactionResponse.of(accountEntity);
    }
}