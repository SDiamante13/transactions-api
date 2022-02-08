package com.kinandcarta.transactionsapi.repository;

import com.kinandcarta.transactionsapi.domain.entity.Account;
import com.kinandcarta.transactionsapi.domain.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        Account account = new Account(
            123L,
            "Tony Soprano",
            null
        );
        Transaction transaction = new Transaction(
            456L,
            LocalDate.of(2022, 2, 2).toEpochDay(),
            50.00,
            "Amazon",
            "XP Explained Book",
            account
        );
        account.setTransactions(Set.of(transaction));
        accountRepository.save(account);
    }

    @Test
    void findAllByAccount_AccountIdReturnsListOfTransactionsForGivenAccountId() {
        List<Transaction> actualResponse = transactionRepository.findAllByAccount_AccountId(123L);
        assertThat(actualResponse.size()).isEqualTo(1);
        assertThat(actualResponse.get(0).getAccount().getAccountId()).isEqualTo(123L);
    }
}