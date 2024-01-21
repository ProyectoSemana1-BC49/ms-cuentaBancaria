package com.nttdatabc.mscuentabancaria.service;

import com.nttdatabc.mscuentabancaria.model.TypeMovement;
import com.nttdatabc.mscuentabancaria.repository.MovementRepository;
import com.nttdatabc.mscuentabancaria.utils.Utilitarios;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.model.Movement;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.*;

@Service
public class MovementService {
    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private AccountService accountService;

    public void createMovementDepositService(Movement movement) throws ErrorResponseException {
        validateMovementNoNulls(movement);
        validateMovementEmpty(movement);
        verifyTypeMovement(movement);
        verifyValues(movement);
        validateAccountRegister(movement.getAccountId());

        movement.setId(Utilitarios.generateUUID());
        movement.setFecha(LocalDateTime.now().toString());
        movementRepository.save(movement);
        //actualizar monto en cuenta bancaria
        Account accountFound = accountService.getAccountByIdService(movement.getAccountId());
        accountFound.setCurrentBalance(accountFound.getCurrentBalance().add(movement.getMount()));
        accountService.updateAccountServide(accountFound);
    }

    public void createWithDrawService(Movement movement) throws ErrorResponseException{
        verifyTypeMovement(movement);
        validateMovementNoNulls(movement);
        validateMovementEmpty(movement);
        verifyValues(movement);
        validateAccountRegister(movement.getAccountId());

        movement.setId(Utilitarios.generateUUID());
        movement.setFecha(LocalDateTime.now().toString());
        //Validar que el monto de retiro, no sea más que el saldo total
        Account accountFound = accountService.getAccountByIdService(movement.getAccountId());
        if(accountFound.getCurrentBalance().doubleValue() < movement.getMount().doubleValue()){
            throw new ErrorResponseException(EX_ERROR_MOVEMENT_BALANCE_INSUFFICIENT, HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
        }
        movementRepository.save(movement);
        //actualizar monto en cuenta bancaria
        accountFound.setCurrentBalance(accountFound.getCurrentBalance().subtract(movement.getMount()));
        accountService.updateAccountServide(accountFound);
    }

    public List<Movement>getMovementsByAccountIdService(String accountId) throws ErrorResponseException{
        validateAccountRegister(accountId);
        return movementRepository.findByAccountId(accountId);
    }

    /*=====================================================================================*/
    public  void validateMovementNoNulls(Movement movement) throws ErrorResponseException {
        Optional.of(movement)
                .filter(c -> c.getAccountId() != null)
                .filter(c -> c.getTypeMovement() != null)
                .filter(c -> c.getMount() != null)
                .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public  void validateMovementEmpty(Movement movement) throws ErrorResponseException {
        Optional.of(movement)
                .filter(c -> !c.getAccountId().isBlank())
                .filter(c -> !c.getTypeMovement().isBlank())
                .filter(c -> !c.getMount().toString().isBlank())
                .orElseThrow(() -> new ErrorResponseException(EX_VALUE_EMPTY,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public  void verifyTypeMovement(Movement movement)throws ErrorResponseException{
        Predicate<Movement> existTypeMovement = movementValidate -> movementValidate
                .getTypeMovement()
                .equalsIgnoreCase(TypeMovement.DEPOSITO.toString()) ||
                movementValidate.getTypeMovement().equalsIgnoreCase(TypeMovement.RETIRO.toString());
        if(existTypeMovement.negate().test(movement)){
            throw new ErrorResponseException(EX_ERROR_TYPE_MOVEMENT,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }
    public void validateAccountRegister(String accountId) throws ErrorResponseException {
        accountService.getAccountByIdService(accountId);
    }
    public void verifyValues(Movement movement)throws ErrorResponseException{
        if(movement.getMount().doubleValue() <= VALUE_MIN_ACCOUNT_BANK){
            throw new ErrorResponseException(EX_ERROR_VALUE_MIN_MOVEMENT,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }
}
