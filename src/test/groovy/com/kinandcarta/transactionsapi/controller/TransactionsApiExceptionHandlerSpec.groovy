package com.kinandcarta.transactionsapi.controller

import com.kinandcarta.transactionsapi.domain.exception.AccountNotFoundException
import com.kinandcarta.transactionsapi.domain.exception.Error
import spock.lang.Specification

import static datafixtures.RandomGenerator.randomLong

class TransactionsApiExceptionHandlerSpec extends Specification {
    def "should return Error"() {
        given: "an accountId"
        final def accountId = randomLong()
        final TransactionsApiExceptionHandler target = new TransactionsApiExceptionHandler();

        when: "AccountNotFoundException is thrown"
        def actual = target.handleAccountNotFoundException(new AccountNotFoundException(accountId))

        then: "returned error will capture the Exception class and message from it"
        actual.getError() == new Error(
                "AccountNotFoundException",
                "The card member account with an id of ${accountId} was not found.");
    }

}