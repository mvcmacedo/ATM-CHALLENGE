package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.model.Account;
import br.com.ibm.challenge.service.AccountService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Test
    public void getEmptyAccountListTest() {
        ResponseEntity<List<Account>> result = accountController.getAccounts();

        assertThat(result.getBody()).isEmpty();
    }

    @Test
    public void getAccountTest() {
        final List<Account> accountList = Collections.singletonList(Account.builder()
            .digits("1111").build());

        when(accountService.get()).thenReturn(accountList);

        ResponseEntity<List<Account>> result = accountController.getAccounts();

        assertThat(result.getBody()).hasSize(accountList.size());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void accountNotFoundTest() {
        when(accountService.get(anyString())).thenThrow(NullPointerException.class);

        try {
            accountController.getAccount(anyString());
        } catch (ResponseStatusException e) {
            assertThat(e.getMessage()).contains("Account Not Found");
        }
    }
}
