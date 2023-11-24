package com.kinandcarta.transactionsapi.controller;

import com.kinandcarta.transactionsapi.domain.response.TransactionResponse;
import com.kinandcarta.transactionsapi.service.TransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountTransactionsController {

    private final TransactionsService transactionsService;

    public AccountTransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getAccountTransactions(
            @PathVariable long accountId,
            @RequestParam(required = false) String fromDate
    ) {
        System.out.println("hi im from date " + fromDate);
        return ResponseEntity.ok(transactionsService.getTransactions(accountId, fromDate));
    }
}