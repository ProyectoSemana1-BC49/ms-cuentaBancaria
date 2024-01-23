package com.nttdatabc.mscuentabancaria.repository;

import com.nttdatabc.mscuentabancaria.model.Movement;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository Movement.
 */
@Repository
public interface MovementRepository extends MongoRepository<Movement, String> {
  List<Movement> findByAccountId(String accountId);
}
