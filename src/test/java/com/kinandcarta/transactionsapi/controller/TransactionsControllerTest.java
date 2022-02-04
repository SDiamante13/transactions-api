package com.kinandcarta.transactionsapi.controller;

import com.kinandcarta.transactionsapi.domain.response.AccountTransactionResponse;
import com.kinandcarta.transactionsapi.service.TransactionsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransactionsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionsService mockTransactionsService;

    @BeforeEach
    void setUp() {
        TransactionsController transactionsController = new TransactionsController(mockTransactionsService);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionsController).build();
    }

    @Test
    void returnsEmptyTransactionsWhenServiceReturnsAccountWithNoTransactions() throws Exception {
        given(mockTransactionsService.getTransactions(anyLong()))
            .willReturn(new AccountTransactionResponse(
                123L,
                "Bruce Wayne",
                emptyList()
            ));
        mockMvc.perform(get("/account/{accountId}/transactions", 123))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accountId", Matchers.is("123")))
            .andExpect(jsonPath("$.transactions", Matchers.is("[]")));
    }
}