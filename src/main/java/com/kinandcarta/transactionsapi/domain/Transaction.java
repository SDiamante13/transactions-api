package com.kinandcarta.transactionsapi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TRANSACTIONS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @Column(name = "TRANSACTION_ID")
    private long transactionId;
    private long date;
    private double amount;
    private String merchantName;
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
}
