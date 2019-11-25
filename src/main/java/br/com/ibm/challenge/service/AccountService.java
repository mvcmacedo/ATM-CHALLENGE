package br.com.ibm.challenge.service;

import br.com.ibm.challenge.dto.*;
import br.com.ibm.challenge.helper.MoneyBills;
import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.model.History;
import br.com.ibm.challenge.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.ibm.challenge.helper.HistoryType.DEPOSIT;
import static br.com.ibm.challenge.helper.HistoryType.WITHDRAWAL;

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

    public WithdrawalResponseDTO withdrawal(String id, WithdrawalDTO withdrawal) {
        final Account account = Optional.ofNullable(accountRepository.findById(id))
            .orElseThrow(NullPointerException::new);

        if (account.getBalance() < withdrawal.getAmount()) {
            throw new Error("Balance insufucient");
        }

        final Account updatedAccount = account.toBuilder()
            .balance(account.getBalance() - withdrawal.getAmount())
            .build();

        accountRepository.save(updatedAccount);

        final History history = History.builder()
            .account(id)
            .type(WITHDRAWAL)
            .amount(withdrawal.getAmount())
            .build();

        historyService.create(history);

        return WithdrawalResponseDTO.builder()
            .history(history)
            .balance(updatedAccount.getBalance())
            .moneyBills(MoneyBills.getBills(withdrawal.getAmount()))
            .build();
    }
}
