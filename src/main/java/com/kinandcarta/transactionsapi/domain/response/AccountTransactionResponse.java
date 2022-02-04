package com.kinandcarta.transactionsapi.domain.response;

import com.kinandcarta.transactionsapi.domain.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionResponse {
  private long accountId;
  private String memberName;
  private List<TransactionResponse> transactions;

  public static AccountTransactionResponse of(Account account) {
    return new AccountTransactionResponse(
            account.getAccountId(),
            account.getMemberName(),
            account.getTransactions().stream()
                    .map(TransactionResponse::of)
                    .collect(Collectors.toList()));
  }
}
