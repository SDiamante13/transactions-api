package com.kinandcarta.transactionsapi;

import com.kinandcarta.transactionsapi.domain.Account;
import com.kinandcarta.transactionsapi.repository.AccountRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.emptySet;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionsAcceptanceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        Account account = new Account(123L, "Bruce Wayne", emptySet());
        accountRepository.save(account);
    }

    /***
     * Scenario 1: Retrieve transactions for account with no transactions
     *
     * GIVEN a card member account with no transactions
     * WHEN I request a list of transactions for that account
     * THEN I will receive a success response
     * AND I will see an empty list of transactions
     */
    @Test
    void returnsEmptyTransactionListWhenAccountHasNoTransactions() throws Exception {
        mockMvc.perform(get("/accounts/{accountId}/transactions", 123))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId", Matchers.is("123")))
                .andExpect(jsonPath("$.transactions", Matchers.is("[]")));
    }
}