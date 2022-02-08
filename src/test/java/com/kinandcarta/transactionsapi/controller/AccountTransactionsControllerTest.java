package com.kinandcarta.transactionsapi.controller;

import com.kinandcarta.transactionsapi.domain.exception.AccountNotFoundException;
import com.kinandcarta.transactionsapi.domain.response.TransactionResponse;
import com.kinandcarta.transactionsapi.service.TransactionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountTransactionsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionsService mockTransactionsService;

    @InjectMocks
    private AccountTransactionsController accountTransactionsController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountTransactionsController)
                .setControllerAdvice(new TransactionsApiExceptionHandler())
                .build();
    }

    @Test
    void returnsEmptyTransactionsWhenServiceReturnsAccountWithNoTransactions() throws Exception {
        given(mockTransactionsService.getTransactions(anyLong()))
                .willReturn(emptyList());

        mockMvc.perform(get("/accounts/{accountId}/transactions", 123))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void returnsNotFoundErrorMessageWhenServiceThrowsAnAccountNotFoundException() throws Exception {
        given(mockTransactionsService.getTransactions(anyLong()))
                .willThrow(new AccountNotFoundException(999L));

        mockMvc.perform(get("/accounts/{accountId}/transactions", 999))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.type", is("AccountNotFoundException")))
                .andExpect(jsonPath("$.error.message",
                                is("The card member account with an id of 999 was not found.")
                        )
                );
    }

    @Test
    void returnsTransactionsWhenServiceReturnsAccountWithTransactions() throws Exception {
        given(mockTransactionsService.getTransactions(anyLong()))
                .willReturn(
                        singletonList(new TransactionResponse(
                                "1",
                                "2022-02-01",
                                200.00,
                                "Amazon",
                                "XP Explained (Book)",
                                "123"
                        ))
                );

        mockMvc.perform(get("/accounts/{accountId}/transactions", 123))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$.[0].accountId", is("123")))
                .andExpect(jsonPath("$.[0].transactionId", is("1")));
    }
}