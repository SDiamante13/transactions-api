package com.kinandcarta.transactionsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "ACCOUNTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private long accountId;

    private String memberName;

    @OneToMany(mappedBy = "ACCOUNTS")
    private Set<Transaction> transactions;
}
