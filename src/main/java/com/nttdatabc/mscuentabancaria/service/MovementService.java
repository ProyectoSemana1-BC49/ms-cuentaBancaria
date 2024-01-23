package com.nttdatabc.mscuentabancaria.service;

import com.nttdatabc.mscuentabancaria.model.Movement;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import java.util.List;

/**
 * Interface movementService.
 */
public interface MovementService {
  void createMovementDepositService(Movement movement) throws ErrorResponseException;

  void createWithDrawService(Movement movement) throws ErrorResponseException;

  List<Movement> getMovementsByAccountIdService(String accountId) throws ErrorResponseException;
}
