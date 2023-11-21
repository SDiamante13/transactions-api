package com.kinandcarta.transactionsapi.service

import com.kinandcarta.transactionsapi.domain.entity.Account
import com.kinandcarta.transactionsapi.domain.entity.Transaction
import com.kinandcarta.transactionsapi.domain.response.TransactionResponse
import com.kinandcarta.transactionsapi.repository.AccountRepository
import com.kinandcarta.transactionsapi.repository.TransactionRepository
import spock.lang.Specification

import java.time.LocalDate

class TransactionServicesSpec extends Specification {
    private TransactionRepository transactionRepositoryMock;
    private AccountRepository accountRepositoryMock
    private TransactionsService transactionsService

    def setup() {
        transactionRepositoryMock = Mock(TransactionRepository)
        accountRepositoryMock = Mock(AccountRepository)
        transactionsService = new TransactionsService(transactionRepositoryMock, accountRepositoryMock)
    }

    def "should retrieve transaction with a date equal to or greater than"() {
        given: "fromDate"
        final def fromDate = "2023-11-19"
        final def startDate = LocalDate.parse(fromDate)
        final def accountId = 21324L
        final def aTransaction = new Transaction(
                transactionId: 123L,
                date: startDate.toEpochDay(),
                amount: 100,
                merchantName: "Some Merchant Name",
                summary: "Some Summary",
                account: new Account(accountId: 0)
        )

        when: "transaction service is invoked with an accountId and fromDate"
        def actual = transactionsService.getTransactions(accountId, fromDate)

        then: "will return expected transaction"
        verifyAll {
            1 * transactionRepositoryMock
                    .findAllByAccountAccountIdAndDateGreaterThanEqual(accountId, startDate.toEpochDay()) >> [aTransaction]
            0 * accountRepositoryMock.findById(accountId)
        }
        actual == [TransactionResponse.of(aTransaction)]
    }

    def "should retrieve transaction"() {
        given: "an accountId"
        final def accountId = 21324L
        final def aTransaction = new Transaction(
                transactionId: 123L,
                date: LocalDate.parse("2023-11-19").toEpochDay(),
                amount: 100,
                merchantName: "Some Merchant Name",
                summary: "Some Summary",
                account: new Account(accountId: 0)
        )

        when: "transaction service is invoked with an accountId"
        def actual = transactionsService.getTransactions(accountId, null)

        then: "will return expected transaction"
        verifyAll {
            1 * transactionRepositoryMock
                    .findAllByAccount_AccountId(accountId) >> [aTransaction]
            0 * accountRepositoryMock.findById(accountId)
        }
        actual == [TransactionResponse.of(aTransaction)]
    }

    def "should retrieve transaction"() {
        given: "an accountId"
        final def accountId = 21324L
        final def aTransaction = new Transaction(
                transactionId: 123L,
                date: LocalDate.parse("2023-11-19").toEpochDay(),
                amount: 100,
                merchantName: "Some Merchant Name",
                summary: "Some Summary",
                account: new Account(accountId: 0)
        )

        when: "transaction service is invoked with an accountId"
        def actual = transactionsService.getTransactions(accountId, null)

        then: "will return expected transaction"
        verifyAll {
            1 * transactionRepositoryMock
                    .findAllByAccount_AccountId(accountId) >> []
        }
    }
}