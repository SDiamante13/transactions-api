package com.kinandcarta.transactionsapi.service

import com.kinandcarta.transactionsapi.domain.entity.Account
import com.kinandcarta.transactionsapi.domain.entity.Transaction
import com.kinandcarta.transactionsapi.domain.exception.AccountNotFoundException
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

    def "should retrieve transaction with a date equal to or greater than fromDate"() {
        given: "a fromDate and an account Id"
        final def fromDate = "2023-11-19"
        final def startDate = LocalDate.parse(fromDate)
        final def accountId = 21324L
        final def transaction = new Transaction(
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
                    .findAllByAccountAccountIdAndDateGreaterThanEqual(accountId, startDate.toEpochDay()) >> [transaction]
            0 * accountRepositoryMock.findById(accountId)
            0 * transactionRepositoryMock.findAllByAccount_AccountId(_ as String)
        }
        actual == [TransactionResponse.of(transaction)]
    }

    def "should retrieve transaction without a fromDate"() {
        given: "an accountId"
        final def accountId = 21324L
        final def transaction = new Transaction(
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
                    .findAllByAccount_AccountId(accountId) >> [transaction]
            0 * accountRepositoryMock.findById(accountId)
            0 * transactionRepositoryMock.findAllByAccountAccountIdAndDateGreaterThanEqual(_ as String, _ as long)
        }
        actual == [TransactionResponse.of(transaction)]
    }

    def "should retrieve transaction"() {
        given: "an accountId"
        final def accountId = 21324L
        final def transaction = new Transaction(
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
            1 * transactionRepositoryMock.findAllByAccount_AccountId(accountId) >> List.of()
            1 * accountRepositoryMock.findById(accountId)
                    >> Optional.of(new Account(accountId: accountId, memberName: "Locke", transactions: [transaction]))
            0 * transactionRepositoryMock.findAllByAccountAccountIdAndDateGreaterThanEqual(_ as String, _ as long)
        }
        actual.isEmpty()
    }

    def "should retrieve transaction"() {
        given: "an accountId"
        final def accountId = 21324L

        transactionRepositoryMock.findAllByAccount_AccountId(accountId) >> List.of()
        accountRepositoryMock.findById(accountId) >> Optional.empty()

        when: "transaction service is invoked with an accountId"
        transactionsService.getTransactions(accountId, null)

        then: "will return expected transaction"
        def actual = thrown(AccountNotFoundException)
        actual.message == "The card member account with an id of ${accountId} was not found."
    }
}