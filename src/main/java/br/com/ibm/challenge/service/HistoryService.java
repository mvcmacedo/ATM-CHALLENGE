package br.com.ibm.challenge.service;

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

    public History create(History history) {
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
        return Optional.ofNullable(historyRepository.findByAccount(id))
            .orElse(Collections.emptyList());
    }
}
