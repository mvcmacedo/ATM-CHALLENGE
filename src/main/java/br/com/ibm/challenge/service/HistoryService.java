package br.com.ibm.challenge.service;

import br.com.ibm.challenge.helper.HistoryType;
import br.com.ibm.challenge.model.History;
import br.com.ibm.challenge.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    History create(History history) {
        return historyRepository.save(history);
    }

    public List<History> get() {
        return Optional.ofNullable(historyRepository.findAll())
            .orElse(Collections.emptyList());
    }

    public History get(String id) {
        return Optional.ofNullable(historyRepository.findById(id))
            .orElseThrow(NullPointerException::new);
    }

    public List<History> getByAccountId(String id) {
        final List<History> history = Optional.ofNullable(historyRepository.findByAccount(id))
            .orElse(Collections.emptyList());

        Collections.reverse(history);

        return history;
    }

    History createTransferHistory(String origin, String destination, HistoryType type, Double amount) {
        final History history = History.builder()
            .type(type)
            .amount(amount)
            .account(origin)
            .transfer_account(destination)
            .build();

        return this.create(history);
    }
}
