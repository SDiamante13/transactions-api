package com.kinandcarta.transactionsapi.controller;

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
        mockMvc = MockMvcBuilders.standaloneSetup(accountTransactionsController).build();
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
}