package com.kinandcarta.transactionsapi.bootstrap;

import com.kinandcarta.transactionsapi.domain.entity.Account;
import com.kinandcarta.transactionsapi.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptySet;

@Component
public class DataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public DataLoader(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {
        Account account = new Account(
            123L,
            "Tony Soprano",
            emptySet()
        );
        accountRepository.save(account);
    }
}
