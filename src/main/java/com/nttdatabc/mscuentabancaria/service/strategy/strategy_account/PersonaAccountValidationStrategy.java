package com.nttdatabc.mscuentabancaria.service.strategy.strategy_account;

import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.*;

public class PersonaAccountValidationStrategy implements AccountValidationStrategy{
    @Override
    public void validateAccount(Account account, List<Account> accountList) throws ErrorResponseException {
        if(account.getHolders() != null){
            throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_PERSONA_NOT_HOLDERS, HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
        }
        if(accountList.size() >= MAX_SIZE_ACCOUNT_CUSTOMER_PERSONA){
            throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_PERSONA, HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
        }
    }
}
