package br.com.ibm.challenge.repository;

import br.com.ibm.challenge.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<History, Long> {
}
