package com.nttdatabc.mscuentabancaria.service.strategy.strategy_account;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_CONFLICTO_CUSTOMER_PERSONA;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_CONFLICTO_CUSTOMER_PERSONA_NOT_HOLDERS;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.MAX_SIZE_ACCOUNT_CUSTOMER_PERSONA;

import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * Persona account validation.
 */
public class PersonaAccountValidationStrategy implements AccountValidationStrategy {
  @Override
  public void validateAccount(Account account, List<Account> accountList) throws ErrorResponseException {
    if (account.getHolders() != null) {
      throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_PERSONA_NOT_HOLDERS,
          HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
    }
    if (accountList.size() >= MAX_SIZE_ACCOUNT_CUSTOMER_PERSONA) {
      throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_PERSONA,
          HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
    }
  }
}
