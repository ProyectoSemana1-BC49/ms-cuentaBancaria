package com.nttdatabc.mscuentabancaria.service;

import com.nttdatabc.mscuentabancaria.model.TypeMovement;
import com.nttdatabc.mscuentabancaria.repository.MovementRepository;
import com.nttdatabc.mscuentabancaria.utils.Utilitarios;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.nttdatabc.mscuentabancaria.model.Account;
import org.nttdatabc.mscuentabancaria.model.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        validateAccountRegister(movement.getAccountId());
        movement.setId(Utilitarios.generateUUID());
        movementRepository.save(movement);
    }

    public void createWithDrawService(Movement movement) throws ErrorResponseException{
        verifyTypeMovement(movement);
        validateMovementNoNulls(movement);
        validateMovementEmpty(movement);
        validateAccountRegister(movement.getAccountId());
        movement.setId(Utilitarios.generateUUID());
        movementRepository.save(movement);
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
                .filter(c -> c.getFecha() != null)
                .filter(c -> c.getMount() != null)
                .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public  void validateMovementEmpty(Movement movement) throws ErrorResponseException {
        Optional.of(movement)
                .filter(c -> !c.getAccountId().isBlank())
                .filter(c -> !c.getTypeMovement().isBlank())
                .filter(c -> !c.getFecha().isBlank())
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
}
