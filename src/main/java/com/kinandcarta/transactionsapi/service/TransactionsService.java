package com.kinandcarta.transactionsapi.service;

import com.kinandcarta.transactionsapi.domain.entity.Transaction;
import com.kinandcarta.transactionsapi.domain.response.TransactionResponse;
import com.kinandcarta.transactionsapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class TransactionsService {
    private final TransactionRepository transactionRepository;

    public TransactionsService(TransactionRepository accountRepository) {
        this.transactionRepository = accountRepository;
    }

    public List<TransactionResponse> getTransactions(long accountId) {
        List<Transaction> transactionList = transactionRepository.findAllByAccount_AccountId(accountId);
        return emptyList();
    }
}