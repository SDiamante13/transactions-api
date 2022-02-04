package com.kinandcarta.transactionsapi.controller;

import com.kinandcarta.transactionsapi.domain.response.AccountTransactionResponse;
import com.kinandcarta.transactionsapi.service.TransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountTransactionsController {

  private final TransactionsService transactionsService;

  public AccountTransactionsController(TransactionsService transactionsService) {
    this.transactionsService = transactionsService;
  }

  @GetMapping("/{accountId}/transactions")
  public ResponseEntity<AccountTransactionResponse> getAccountTransactions(@PathVariable long accountId) {
    return ResponseEntity.ok(transactionsService.getTransactions(accountId));
  }
}