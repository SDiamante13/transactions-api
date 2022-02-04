package com.kinandcarta.transactionsapi.service;

import com.kinandcarta.transactionsapi.domain.response.AccountTransactionResponse;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {
  public AccountTransactionResponse getTransactions(long accountId) {
    return new AccountTransactionResponse();
  }
}