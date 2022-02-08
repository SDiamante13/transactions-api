package com.kinandcarta.transactionsapi;

import com.kinandcarta.transactionsapi.domain.entity.Account;
import com.kinandcarta.transactionsapi.domain.entity.Transaction;
import com.kinandcarta.transactionsapi.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TransactionsAcceptanceTest {

    private static final int ACCOUNT_ID_WITH_TRANSACTIONS = 555;
    private static final int ACCOUNT_ID_WITHOUT_TRANSACTIONS = 123;
    private static final String MEMBER_NAME = "Bruce Wayne";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        Account accountWithoutTransactions = new Account(ACCOUNT_ID_WITHOUT_TRANSACTIONS, MEMBER_NAME, emptySet());
        Account accountWithTransactions = new Account(ACCOUNT_ID_WITH_TRANSACTIONS, MEMBER_NAME, null);
        accountWithTransactions.setTransactions(Set.of(
            new Transaction(
                1L,
                LocalDate.of(2022, 2, 1).toEpochDay(),
                50.00,
                "Amazon",
                "XP Explained (Book)",
                accountWithTransactions
            ),
            new Transaction(
                2L,
                LocalDate.of(2022, 2, 2).toEpochDay(),
                350.00,
                "Walmart",
                "Standing Desk",
                accountWithTransactions
            )
        ));
        accountRepository.save(accountWithoutTransactions);
        accountRepository.save(accountWithTransactions);
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
     * Scenario 3: Retrieve transactions for an account with one or more transactions
     *
     * GIVEN a card member account with transactions
     * WHEN I request a list of transactions for that account
     * THEN I will receive a success response
     * AND I will a list of all their transactions
     */
    @Test
    void returnsTransactionsForACardMemberAccountThatHasOneOrMoreTransactions() throws Exception {
        String expectedResponse = JSONTestUtils.readFile("expectedTransactionsResponse.json");

        String actualResponse = mockMvc.perform(get("/accounts/{accountId}/transactions", ACCOUNT_ID_WITH_TRANSACTIONS))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }
}
