package br.com.ibm.challenge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ibm.challenge.dto.AccountDTO;
import br.com.ibm.challenge.dto.DepositDTO;
import br.com.ibm.challenge.dto.TransferDTO;
import br.com.ibm.challenge.dto.WithdrawalDTO;
import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.repository.AccountRepository;

import java.util.List;
import java.util.Collections;

import static java.lang.Boolean.TRUE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ATMService atmService;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void getAccountsTest() {
        final List<Account> accountMock = Collections.singletonList(Account.builder()
            .balance(100d).digits("1111").build());

        when(accountRepository.findAll()).thenReturn(accountMock);

        final List<Account> result = accountService.get();

        assertThat(result).hasSize(accountMock.size());
    }

    @Test
    public void createAccountTest() {
        final String digits = "1111";
        final Account createdAccount = Account.builder().digits(digits).build();
        final AccountDTO accountDTO = AccountDTO.builder().digits(digits).build();

        when(accountRepository.save(any(Account.class))).thenReturn(createdAccount);

        final Account result = accountService.create(accountDTO);

        assertThat(result).isEqualTo(createdAccount);
    }

    @Test
    public void getAccountByIdTest() {
        final String digits = "1111";
        final Account account = Account.builder().digits(digits).build();

        when(accountRepository.findById(anyString())).thenReturn(account);

        final Account result = accountService.get("test");

        assertThat(result).isEqualTo(account);
        assertThat(result.getDigits()).isEqualToIgnoringCase(digits);
    }

    @Test(expected = Error.class)
    public void depositWithATMClosedTest() {
        final DepositDTO depositDTO = DepositDTO.builder().build();

        when(atmService.isClosed()).thenReturn(TRUE);

        accountService.deposit("1111", depositDTO);
    }

    @Test(expected = Error.class)
    public void withdrawalWithATMClosedTest() {
        final WithdrawalDTO withdrawalDTO = WithdrawalDTO.builder().build();

        when(atmService.isClosed()).thenReturn(TRUE);

        accountService.withdrawal("1111", withdrawalDTO);
    }

    @Test(expected = Error.class)
    public void transferWithATMClosedTest() {
        final TransferDTO transferDTO = TransferDTO.builder().build();

        when(atmService.isClosed()).thenReturn(TRUE);

        accountService.transfer("1111", transferDTO);
    }

    @Test(expected = NullPointerException.class)
    public void getAccountFailedTest() {
        accountService.get("1111");
    }
}
