package com.kinandcarta.transactionsapi.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionResponse {
  private long accountId;
  private String memberName;
  private List<TransactionResponse> transactions;
}
