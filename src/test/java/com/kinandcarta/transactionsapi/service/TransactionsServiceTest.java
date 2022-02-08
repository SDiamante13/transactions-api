package com.kinandcarta.transactionsapi.service;

import com.kinandcarta.transactionsapi.domain.entity.Account;
import com.kinandcarta.transactionsapi.domain.entity.Transaction;
import com.kinandcarta.transactionsapi.domain.entity.Account;
import com.kinandcarta.transactionsapi.domain.exception.AccountNotFoundException;
import com.kinandcarta.transactionsapi.domain.response.TransactionResponse;
import com.kinandcarta.transactionsapi.repository.AccountRepository;
import com.kinandcarta.transactionsapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TransactionsServiceTest {

    private static final long ACCOUNT_ID = 123L;
    public static final Account ACCOUNT = new Account(
            123L,
            "Tony Soprano",
            null
    );
    public static final Transaction TRANSACTION = new Transaction(
            1L,
            LocalDate.of(2022, 2, 1).toEpochDay(),
            50.00,
            "Amazon",
            "XP Explained (Book)",
            null
    );

    public static final TransactionResponse TRANSACTION_RESPONSE =
            new TransactionResponse(
                    "1",
                    "2022-02-01",
                    50.00,
                    "Amazon",
                    "XP Explained (Book)",
                    "123"
            );

    @Mock
    private TransactionRepository mockTransactionRepository;
    @Mock
    private AccountRepository mockAccountRepository;

    private TransactionsService transactionsService;

    @BeforeEach
    void setUp() {
        TRANSACTION.setAccount(ACCOUNT);
        transactionsService = new TransactionsService(mockTransactionRepository, mockAccountRepository);
    }

    @Test
    void returnsTransactionResponseWithNoTransactionsWhenThereAreNoTransactionsPresentForGivenAccount() {
        given(mockTransactionRepository.findAllByAccount_AccountId(anyLong()))
                .willReturn(emptyList());
        given(mockAccountRepository.findById(anyLong()))
                .willReturn(Optional.of(new Account()));

        List<TransactionResponse> actualResponse = transactionsService.getTransactions(123L);

        assertThat(actualResponse).isEqualTo(emptyList());
    }

    @Test
    void throwsAccountNotFoundExceptionWhenAccountIsNotPresentForTheGivenAccountId() {
        given(mockTransactionRepository.findAllByAccount_AccountId(anyLong()))
                .willReturn(emptyList());
        given(mockAccountRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> transactionsService.getTransactions(123L)
        );
    }

    @Test
    void returnsAccountTransactionResponseWithTransactionsWhenThereAreTransactionsPresentForGivenAccount() {
        given(mockTransactionRepository.findAllByAccount_AccountId(anyLong()))
                .willReturn(List.of(TRANSACTION));

        List<TransactionResponse> actualResponse = transactionsService.getTransactions(ACCOUNT_ID);

        assertThat(actualResponse)
                .isEqualTo(List.of(
                        TRANSACTION_RESPONSE
                ));
    }
}