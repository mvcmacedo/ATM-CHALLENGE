package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.dto.*;
import br.com.ibm.challenge.helper.MoneyBills;
import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountDTO account) {
        return ResponseEntity.ok(accountService.create(account));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable String id) {
        try {
            return ResponseEntity.ok(accountService.get(id));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(
        @PathVariable String id,
        @RequestBody AccountDTO account) {
        try {
            return ResponseEntity.ok(accountService.update(id, account));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id) {
        try {
            accountService.delete(id);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Delete Account Failed", e);
        }

        return ResponseEntity.ok(id);
    }

    @PutMapping("/deposit/{id}")
    public ResponseEntity<ConfirmationDTO> deposit(@PathVariable String id, @RequestBody DepositDTO deposit) {
        try {
            return ResponseEntity.ok(accountService.deposit(id, deposit));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found", e);
        }
    }

    @PutMapping("/withdrawal/{id}")
    public ResponseEntity<WithdrawalResponseDTO> withdrawal(@PathVariable String id, @RequestBody WithdrawalDTO withdrawal) {
        try {
            return ResponseEntity.ok(accountService.withdrawal(id, withdrawal));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found", e);
        }
    }
}
