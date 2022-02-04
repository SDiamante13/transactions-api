package com.kinandcarta.transactionsapi.service;

import com.kinandcarta.transactionsapi.domain.entity.Account;
import com.kinandcarta.transactionsapi.domain.entity.Transaction;
import com.kinandcarta.transactionsapi.domain.response.AccountTransactionResponse;
import com.kinandcarta.transactionsapi.domain.response.TransactionResponse;
import com.kinandcarta.transactionsapi.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionsService {
    private final AccountRepository accountRepository;

    public TransactionsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountTransactionResponse getTransactions(long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        return convertToAccountTransactionResponse(optionalAccount.get());
    }

    private AccountTransactionResponse convertToAccountTransactionResponse(Account account) {
        return new AccountTransactionResponse(
                account.getAccountId(),
                account.getMemberName(),
                convertToTransactionResponse(account.getTransactions())
        );
    }

    private List<TransactionResponse> convertToTransactionResponse(Set<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> new TransactionResponse(
                        String.valueOf(transaction.getTransactionId()),
                        getDateAsString(transaction.getDate()),
                        transaction.getAmount(),
                        transaction.getMerchantName(),
                        transaction.getSummary()
                )).collect(Collectors.toList());
    }

    private String getDateAsString(long date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormatter.format(LocalDate.ofEpochDay(date));
    }
}