package com.kinandcarta.transactionsapi.repository;

import com.kinandcarta.transactionsapi.domain.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByAccount_AccountId(long accountId);
}