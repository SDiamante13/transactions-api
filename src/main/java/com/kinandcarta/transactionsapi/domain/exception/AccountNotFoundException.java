package com.kinandcarta.transactionsapi.domain.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(long accountId) {
        super("The card member account with an id of " + accountId + " was not found.");
    }
}
