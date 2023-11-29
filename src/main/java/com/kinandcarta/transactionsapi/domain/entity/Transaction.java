package com.kinandcarta.transactionsapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private long transactionId;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private long date;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private double amount;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String merchantName;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String summary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Account account;

    private Transaction(Builder builder) {
        setTransactionId(builder.transactionId);
        setDate(builder.date);
        setAmount(builder.amount);
        setMerchantName(builder.merchantName);
        setSummary(builder.summary);
        setAccount(builder.account);
    }

    public static final class Builder {
        private long transactionId;
        private long date;
        private double amount;
        private String merchantName;
        private String summary;
        private Account account;

        public Builder() {
        }

        public Builder transactionId(long val) {
            transactionId = val;
            return this;
        }

        public Builder date(long val) {
            date = val;
            return this;
        }

        public Builder amount(double val) {
            amount = val;
            return this;
        }

        public Builder merchantName(String val) {
            merchantName = val;
            return this;
        }

        public Builder summary(String val) {
            summary = val;
            return this;
        }

        public Builder account(Account val) {
            account = val;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
