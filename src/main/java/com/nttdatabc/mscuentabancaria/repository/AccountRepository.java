package com.nttdatabc.mscuentabancaria.repository;

import com.nttdatabc.mscuentabancaria.model.Account;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository de account.
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
  List<Account> findByCustomerId(String customerId);
}
