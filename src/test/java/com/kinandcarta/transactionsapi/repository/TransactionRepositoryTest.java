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
        initializeDatabase();
    }

    @Test
    void findAllByAccount_AccountIdReturnsListOfTransactionsForGivenAccountId() {
        final List<Transaction> actualResponse = transactionRepository.findAllByAccount_AccountId(123L);

        assertThat(actualResponse.size()).isEqualTo(1);
        assertThat(actualResponse.get(0).getAccount().getAccountId()).isEqualTo(123L);
    }

    @Test
    void findAllByAccountAccountIdAndDateGreaterThanEqual_shouldReturnTransactionsOnOrAfterTheTransactionDate() {
        final List<Transaction> actualTransactions = transactionRepository.findAllByAccountAccountIdAndDateGreaterThanEqual(789L, LocalDate.of(2022, 2, 2).toEpochDay());

        assertThat(actualTransactions.size()).isEqualTo(2);
        assertThat(actualTransactions).extracting("transactionId")
                .contains(2L, 3L);
    }

    private void initializeDatabase() {
        saveAccount1();
        saveAccount2();
    }

    private void saveAccount1() {
        final Account account1 = new Account(
                789L,
                "Bruce Wayne",
                null
        );
        account1.setTransactions(Set.of(
                aTransactionWith(account1, LocalDate.of(2022, 2, 3).toEpochDay(), 3L),
                aTransactionWith(account1, LocalDate.of(2022, 2, 2).toEpochDay(), 2L),
                aTransactionWith(account1, LocalDate.of(2022, 2, 1).toEpochDay(), 1L)
        ));
        accountRepository.save(account1);
    }

    private void saveAccount2() {
        final Account account2 = new Account(
                123L,
                "Tony Soprano",
                null
        );
        final Transaction transaction = new Transaction(
                456L,
                LocalDate.of(2022, 2, 2).toEpochDay(),
                50.00,
                "Amazon",
                "XP Explained Book",
                account2
        );
        account2.setTransactions(Set.of(transaction));
        accountRepository.save(account2);
    }

    private Transaction aTransactionWith(Account account, long date, long transactionId) {
        return new Transaction(
                transactionId,
                date,
                50.00,
                "Amazon",
                "TDD By Example",
                account
        );
    }
}