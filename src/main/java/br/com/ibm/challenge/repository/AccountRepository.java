package br.com.ibm.challenge.repository;

import br.com.ibm.challenge.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, Long> {
    Account findById(String id);
}
