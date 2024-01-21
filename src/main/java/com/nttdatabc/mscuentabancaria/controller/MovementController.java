package com.nttdatabc.mscuentabancaria.controller;

import com.nttdatabc.mscuentabancaria.service.MovementService;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import com.nttdatabc.mscuentabancaria.api.MovementApi;
import com.nttdatabc.mscuentabancaria.model.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.PREFIX_PATH;

@RestController
@RequestMapping(PREFIX_PATH)
public class MovementController implements MovementApi {
    @Autowired
    private MovementService movementService;

    @Override
    public ResponseEntity<Void> createMovementDeposit(Movement movement) {
        try {
            movementService.createMovementDepositService(movement);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> createWithDraw(Movement movement) {
        try {
            movementService.createWithDrawService(movement);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Movement>> getMovementsByAccountId(String accountId) {
        List<Movement> listFound = null;
        try {
            listFound = movementService.getMovementsByAccountIdService(accountId);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(listFound, HttpStatus.OK);
    }
}
