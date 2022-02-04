package com.kinandcarta.transactionsapi.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTIONS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    private long transactionId;
    private long date;
    private double amount;
    private String merchantName;
    private String summary;
}
