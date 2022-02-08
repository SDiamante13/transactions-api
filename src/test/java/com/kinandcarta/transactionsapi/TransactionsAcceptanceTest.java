package com.kinandcarta.transactionsapi;

import com.kinandcarta.transactionsapi.domain.entity.Account;
import com.kinandcarta.transactionsapi.repository.AccountRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.emptySet;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
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

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
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
                .andExpect(jsonPath("$").isEmpty());
    }

    /***
     * Scenario 2: Account does not exist in database
     *
     * GIVEN a card member account id that does not exist
     * WHEN I request a list of transactions for that account
     * THEN I will receive a not found response
     * AND I will see a detailed not found error message
     */
    @Test
    void returnsNotFoundErrorMessageWhenAccountDoesNotExistInDatabase() throws Exception {
        mockMvc.perform(get("/accounts/{accountId}/transactions", 999))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.type", is("AccountNotFoundException")))
                .andExpect(jsonPath("$.error.message",
                        is("The card member account with an id of 999 was not found."))
                );
    }
}
