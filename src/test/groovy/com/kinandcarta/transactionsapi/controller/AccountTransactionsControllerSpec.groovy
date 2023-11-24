package com.kinandcarta.transactionsapi.controller

import com.kinandcarta.transactionsapi.domain.entity.Transaction
import com.kinandcarta.transactionsapi.service.TransactionsService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static datafixtures.RandomGenerator.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccountTransactionsControllerSpec extends Specification {
    private TransactionsService transactionsServiceMock
    private MockMvc mockMvc;
    private AccountTransactionsController target;

    def setup() {
        transactionsServiceMock = Mock(TransactionsService)
        target = new AccountTransactionsController(transactionsServiceMock)
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .setControllerAdvice(new TransactionsApiExceptionHandler())
                .build()
    }

    def "lessgo"() {
        given: "transactions"
        def transaction = new Transaction()

        transaction.with {
            dat:randomLong()
            amount: randomBigDecimal()
            transactionId: randomLong()
            merchantName: randomString()
            summary: randomString()
        }

        1 * transactionsServiceMock.getTransactions(123, null) >> [transaction]

        expect: "resdults"
        mockMvc.perform(get("/accounts/{accountId}/transactions", 123))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$[0].transactionId').value(0))
                .andExpect(jsonPath('$[0].date').value(transaction.date))
                .andExpect(jsonPath('$[0].amount').value(transaction.amount))
                .andExpect(jsonPath('$[0].merchantName').value(null))
                .andExpect(jsonPath('$[0].summary').value(null))


    }
}