package com.kinandcarta.transactionsapi.repository;

import com.kinandcarta.transactionsapi.domain.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}
