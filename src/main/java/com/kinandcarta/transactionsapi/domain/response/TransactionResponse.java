package com.kinandcarta.transactionsapi.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private String date;
    private double amount;
    private String merchantName;
    private String summary;
    private String accountId;
}