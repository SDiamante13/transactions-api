package com.kinandcarta.transactionsapi.controller

import com.kinandcarta.transactionsapi.service.TransactionsService
import datafixtures.TransactionBuilder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static datafixtures.RandomGenerator.randomInt
import static datafixtures.RandomGenerator.randomString
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

    def "should retrieve transactions without a fromDate in request parameter"() {
        given: "an accountId as a path variable"
        def transaction = new TransactionBuilder().build()
        def accountId = randomInt();

        when: "request is made against registered request handler with the accountId"
        mockMvc.perform(get("/accounts/{accountId}/transactions", accountId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$[0].transactionId').value(transaction.transactionId))
                .andExpect(jsonPath('$[0].date').value(transaction.date))
                .andExpect(jsonPath('$[0].amount').value(transaction.amount))
                .andExpect(jsonPath('$[0].merchantName').value(transaction.merchantName))
                .andExpect(jsonPath('$[0].summary').value(transaction.summary))

        then: "get transactions will be invoked with accountId and will return expected transactions"
        1 * transactionsServiceMock.getTransactions(accountId, null) >> [transaction]
    }

    def "should retrieve with a fromDate in request parameter"() {
        given: "an account Id as a path variable and fromDate as a request parameter"
        def transaction = new TransactionBuilder().build()
        def accountId = randomInt();
        def fromDate = randomString();

        when: "request is made against registered request handler with the accountId and fromDate"
        1 * transactionsServiceMock.getTransactions(accountId, fromDate) >> [transaction]

        then: "get transactions will be invoked with accountId and fromDate and will return expected transactions"
        mockMvc.perform(get("/accounts/{accountId}/transactions", accountId)
                .param("fromDate", fromDate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$[0].transactionId').value(transaction.transactionId))
                .andExpect(jsonPath('$[0].date').value(transaction.date))
                .andExpect(jsonPath('$[0].amount').value(transaction.amount))
                .andExpect(jsonPath('$[0].merchantName').value(transaction.merchantName))
                .andExpect(jsonPath('$[0].summary').value(transaction.summary))
    }
}