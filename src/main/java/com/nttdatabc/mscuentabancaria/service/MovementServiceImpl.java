package com.nttdatabc.mscuentabancaria.service;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_MOVEMENT_BALANCE_INSUFFICIENT;
import static com.nttdatabc.mscuentabancaria.utils.MovementValidator.validateAccountRegister;
import static com.nttdatabc.mscuentabancaria.utils.MovementValidator.validateMovementEmpty;
import static com.nttdatabc.mscuentabancaria.utils.MovementValidator.validateMovementNoNulls;
import static com.nttdatabc.mscuentabancaria.utils.MovementValidator.validateMovements;
import static com.nttdatabc.mscuentabancaria.utils.MovementValidator.verifyTypeMovement;
import static com.nttdatabc.mscuentabancaria.utils.MovementValidator.verifyValues;

import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.model.Movement;
import com.nttdatabc.mscuentabancaria.repository.MovementRepository;
import com.nttdatabc.mscuentabancaria.utils.Utilitarios;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


/**
 * Clase service.
 */
@Service
public class MovementServiceImpl implements MovementService {
  @Autowired
  private MovementRepository movementRepository;
  @Autowired
  private AccountServiceImpl accountServiceImpl;

  @Override
  public void createMovementDepositService(Movement movement) throws ErrorResponseException {
    validateMovementNoNulls(movement);
    validateMovementEmpty(movement);
    verifyTypeMovement(movement);
    verifyValues(movement);
    validateAccountRegister(movement.getAccountId(), accountServiceImpl);

    List<Movement> listMovementByAccount = getMovementsByAccountIdService(movement.getAccountId());
    Account accountFound = accountServiceImpl.getAccountByIdService(movement.getAccountId());
    validateMovements(accountFound, listMovementByAccount);

    movement.setId(Utilitarios.generateUuid());
    movement.setFecha(LocalDateTime.now().toString());
    movementRepository.save(movement);

    //actualizar monto en cuenta bancaria
    Account accountUpdate = accountServiceImpl.getAccountByIdService(movement.getAccountId());
    accountUpdate.setCurrentBalance(accountUpdate.getCurrentBalance().add(movement.getMount()));
    accountServiceImpl.updateAccountServide(accountUpdate);
  }

  @Override
  public void createWithDrawService(Movement movement) throws ErrorResponseException {
    verifyTypeMovement(movement);
    validateMovementNoNulls(movement);
    validateMovementEmpty(movement);
    verifyValues(movement);
    validateAccountRegister(movement.getAccountId(), accountServiceImpl);

    List<Movement> listMovementByAccount = getMovementsByAccountIdService(movement.getAccountId());
    Account accountFound = accountServiceImpl.getAccountByIdService(movement.getAccountId());
    validateMovements(accountFound, listMovementByAccount);

    movement.setId(Utilitarios.generateUuid());
    movement.setFecha(LocalDateTime.now().toString());
    //Validar que el monto de retiro, no sea más que el saldo total
    if (accountFound.getCurrentBalance().doubleValue() < movement.getMount().doubleValue()) {
      throw new ErrorResponseException(EX_ERROR_MOVEMENT_BALANCE_INSUFFICIENT,
          HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
    }
    movementRepository.save(movement);
    //actualizar monto en cuenta bancaria
    accountFound.setCurrentBalance(accountFound.getCurrentBalance().subtract(movement.getMount()));
    accountServiceImpl.updateAccountServide(accountFound);
  }

  @Override
  public List<Movement> getMovementsByAccountIdService(String accountId) throws ErrorResponseException {
    validateAccountRegister(accountId, accountServiceImpl);
    return movementRepository.findByAccountId(accountId);
  }

}
