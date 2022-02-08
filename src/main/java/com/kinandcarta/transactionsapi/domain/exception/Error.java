package com.kinandcarta.transactionsapi.domain.exception;

import lombok.Value;

@Value
public class Error {
    String type;
    String message;
}
