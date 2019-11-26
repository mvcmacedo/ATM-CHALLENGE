package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.model.History;
import br.com.ibm.challenge.service.HistoryService;

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

import static br.com.ibm.challenge.helper.HistoryType.DEPOSIT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HistoryControllerTest {
    @InjectMocks
    private HistoryController historyController;

    @Mock
    private HistoryService historyService;

    @Test
    public void getEmptyHistoryListTest() {
        ResponseEntity<List<History>> result = historyController.get();

        assertThat(result.getBody()).isEmpty();
    }

    @Test
    public void getHistoryTest() {
        final List<History> historyList = Collections.singletonList(History.builder()
            .account("1111").type(DEPOSIT).amount(100d).build());

        when(historyService.get()).thenReturn(historyList);

        ResponseEntity<List<History>> result = historyController.get();

        assertThat(result.getBody()).hasSize(historyList.size());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void historyNotFoundTest() {
        when(historyService.get(anyString())).thenThrow(NullPointerException.class);

        try {
            historyController.get(anyString());
        } catch (ResponseStatusException e) {
            assertThat(e.getMessage()).contains("History Not Found");
        }
    }
}
