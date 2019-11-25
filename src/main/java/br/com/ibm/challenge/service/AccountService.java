package br.com.ibm.challenge.service;

import br.com.ibm.challenge.dto.*;
import br.com.ibm.challenge.helper.HistoryType;
import br.com.ibm.challenge.helper.MoneyBills;
import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.model.History;
import br.com.ibm.challenge.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.ibm.challenge.helper.HistoryType.*;

@Service
public class AccountService {
    @Autowired
    private HistoryService historyService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ATMService atmService;

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

    public Account getByDigits(final String digits) {
        return Optional.ofNullable(accountRepository.findByDigits(digits))
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
        if (atmService.isClosed()) {
            throw new Error("ATM is closed");
        }

        final Account account = Optional.ofNullable(accountRepository.findById(id))
            .orElseThrow(NullPointerException::new);

        final Account updatedAccount = account.toBuilder()
            .balance(account.getBalance() + deposit.getAmount())
            .build();

        accountRepository.save(updatedAccount);

        final History history = History.builder()
            .account(id)
            .amount(deposit.getAmount())
            .deposit_type(deposit.getType())
            .type(DEPOSIT)
            .build();

        historyService.create(history);

        return ConfirmationDTO.builder()
            .account(updatedAccount)
            .history(history)
            .build();
    }

    public WithdrawalResponseDTO withdrawal(String id, WithdrawalDTO withdrawal) {
        if (atmService.isClosed()) {
            throw new Error("ATM is closed");
        }

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
            .money_bills(MoneyBills.getBills(withdrawal.getAmount()))
            .build();
    }

    public History transfer(String id, TransferDTO transferData) {
        if (atmService.isClosed()) {
            throw new Error("ATM is closed");
        }

        final Account originAccount = Optional.ofNullable(accountRepository.findById(id))
            .orElseThrow(NullPointerException::new);

        final Account destinationAccount = Optional.ofNullable(accountRepository
            .findById(transferData.getDestination_account()))
            .orElseThrow(NullPointerException::new);

        if (originAccount.getBalance() < transferData.getAmount()) {
            throw new Error("Balance insufucient");
        }

        accountRepository
            .save(originAccount.toBuilder()
            .balance(originAccount.getBalance() - transferData.getAmount())
            .build());

        accountRepository
            .save(destinationAccount.toBuilder()
            .balance(destinationAccount.getBalance() + transferData.getAmount())
            .build());

        historyService
            .createTransferHistory(transferData.getDestination_account(), id, TRANSFER_RECEIVED, transferData.getAmount());

        return historyService
            .createTransferHistory(id, destinationAccount.getId(), TRANSFER_SENT, transferData.getAmount());
    }
}
