package br.com.ibm.challenge.repository;

import br.com.ibm.challenge.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistoryRepository extends MongoRepository<History, Long> {
    History findById(String id);
    List<History> findByAccount(String account);
}
