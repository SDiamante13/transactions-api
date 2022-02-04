package com.kinandcarta.transactionsapi.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ACCOUNTS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private long accountId;

    private String memberName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "transaction_id")
    @ToString.Exclude
    private Set<Transaction> transactions;
}
