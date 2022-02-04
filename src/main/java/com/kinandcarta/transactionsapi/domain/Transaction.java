package com.kinandcarta.transactionsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private long transactionId;
    private long date;
    private double amount;
    private String merchantName;
    private String summary;
}
