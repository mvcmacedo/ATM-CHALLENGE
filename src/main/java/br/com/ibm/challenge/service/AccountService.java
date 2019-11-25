package br.com.ibm.challenge.service;

import br.com.ibm.challenge.dto.CreateAccount;
import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account create(final CreateAccount createAccount) {
        final Account account = Account.builder()
            .digits(createAccount.getDigits())
            .build();

        return accountRepository.save(account);
    }

    public List<Account> get() {
        return Optional.ofNullable(accountRepository.findAll())
            .orElse(Collections.emptyList());
    }

    public Account get(String id) {
        return Optional.ofNullable(accountRepository.findById(id))
            .orElseThrow(NullPointerException::new);
    }
}
