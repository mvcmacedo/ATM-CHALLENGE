package br.com.ibm.challenge.service;

import br.com.ibm.challenge.model.History;
import br.com.ibm.challenge.repository.HistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import static br.com.ibm.challenge.helper.HistoryType.DEPOSIT;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceTest {
    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryService historyService;

    @Test
    public void getEmptyHistoryTest() {
        when(historyRepository.findAll()).thenReturn(null);

        final List<History> result = historyService.get();

        assertThat(result).isEmpty();
    }

    @Test
    public void getHistoryListTest() {
        final List<History> histories = Collections.singletonList(History.builder()
            .amount(100d)
            .type(DEPOSIT)
            .account("1111")
            .build());

        when(historyRepository.findAll()).thenReturn(histories);

        final List<History> result = historyService.get();

        assertThat(result).hasSize(histories.size());
    }

    @Test
    public void getHistoryByIdTest() {
        final History history = History.builder()
            .amount(100d)
            .type(DEPOSIT)
            .account("1111")
            .build();

        when(historyRepository.findById(anyString())).thenReturn(history);

        final History result = historyService.get("1111");

        assertThat(result).isEqualTo(history);
    }

    @Test
    public void getReversedHistoryListByAccountIdTest() {
        final List<History> historyList = Arrays.asList(History.builder()
                .amount(100d).type(DEPOSIT).account("1111").build(),
            History.builder()
                .amount(100d).type(DEPOSIT).account("2222").build());

        when(historyRepository.findByAccount(anyString())).thenReturn(historyList);

        final List<History> result = historyService.getByAccountId("1111");

        assertThat(result).hasSize(historyList.size());

        assertThat(result.stream().findFirst().get().getAccount())
            .isEqualToIgnoringCase("2222");
    }

    @Test(expected = NullPointerException.class)
    public void getHistoryFailedTest() {
        historyService.get("1111");
    }
}
