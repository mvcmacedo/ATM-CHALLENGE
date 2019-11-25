package br.com.ibm.challenge.service;

import br.com.ibm.challenge.dto.AccountDTO;
import br.com.ibm.challenge.dto.ConfirmationDTO;
import br.com.ibm.challenge.dto.DepositDTO;
import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.model.History;
import br.com.ibm.challenge.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.ibm.challenge.helper.HistoryType.DEPOSIT;

@Service
public class AccountService {
    @Autowired
    private HistoryService historyService;

    @Autowired
    private AccountRepository accountRepository;

    public Account create(final AccountDTO createAccount) {
        final Account account = Account.builder()
            .digits(createAccount.getDigits())
            .build();

        return accountRepository.save(account);
    }

    public List<Account> get() {
        return Optional.ofNullable(accountRepository.findAll())
            .orElse(Collections.emptyList());
    }

    public Account get(final String id) {
        return Optional.ofNullable(accountRepository.findById(id))
            .orElseThrow(NullPointerException::new);
    }

    public Account update(final String id, final AccountDTO account) {
        final Account foundAccount = Optional.ofNullable(accountRepository.findById(id))
            .orElseThrow(NullPointerException::new);

        final Account updatedAccount = foundAccount.toBuilder()
            .digits(account.getDigits())
            .build();

        return accountRepository.save(updatedAccount);
    }

    public void delete(final String id) {
        final Account foundAccount = Optional.ofNullable(accountRepository.findById(id))
            .orElseThrow(NullPointerException::new);

        accountRepository.delete(foundAccount);
    }

    public ConfirmationDTO deposit(final String id, final DepositDTO deposit) {
        final Account account = Optional.ofNullable(accountRepository.findById(id))
            .orElseThrow(NullPointerException::new);

        final Account updatedAccount = account.toBuilder()
            .balance(account.getBalance() + deposit.getAmount())
            .build();

        accountRepository.save(updatedAccount);

        final History history = History.builder()
            .account(id)
            .amount(deposit.getAmount())
            .depositType(deposit.getType())
            .type(DEPOSIT)
            .build();

        historyService.create(history);

        return ConfirmationDTO.builder()
            .account(updatedAccount)
            .history(history)
            .build();
    }
}
