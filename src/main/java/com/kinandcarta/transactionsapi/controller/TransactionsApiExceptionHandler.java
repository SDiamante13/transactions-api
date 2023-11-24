package com.kinandcarta.transactionsapi.controller;

import com.kinandcarta.transactionsapi.domain.exception.AccountNotFoundException;
import com.kinandcarta.transactionsapi.domain.exception.Error;
import com.kinandcarta.transactionsapi.domain.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionsApiExceptionHandler {

    @ExceptionHandler(value = {AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleAccountNotFoundException(Exception ex) {
        return buildErrorResponse(ex);
    }

    private ErrorResponse buildErrorResponse(Exception ex) {
        String type = ex.getClass().getSimpleName();
        String message = ex.getMessage();
        return new ErrorResponse(new Error(type, message));
    }
}
