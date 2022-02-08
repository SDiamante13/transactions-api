package com.kinandcarta.transactionsapi.service;

import com.kinandcarta.transactionsapi.domain.response.TransactionResponse;
import com.kinandcarta.transactionsapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TransactionsServiceTest {

    @Mock
    private TransactionRepository mockTransactionRepository;

    private TransactionsService transactionsService;

    @BeforeEach
    void setUp() {
        transactionsService = new TransactionsService(mockTransactionRepository);
    }

    @Test
    void returnsAccountTransactionResponseWithNoTransactionsWhenThereAreNoTransactionsPresentForGivenAccount() {
        given(mockTransactionRepository.findAllByAccount_AccountId(anyLong()))
                .willReturn(emptyList());

        List<TransactionResponse> actualResponse = transactionsService.getTransactions(123L);

        assertThat(actualResponse).isEqualTo(emptyList());
    }
}