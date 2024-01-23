package com.nttdatabc.mscuentabancaria.service.strategy.strategy_account;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_CONFLICTO_CUSTOMER_EMPRESA_NEED_HOLDERS;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_CONFLICTO_CUSTOMER_EMPRESA_NOT_TYPE_AUTHORIZED;

import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.model.TypeAccountBank;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * Empresa validation.
 */
public class EmpresaAccountValidationStrategy implements AccountValidationStrategy {
  @Override
  public void validateAccount(Account account, List<Account> accountList) throws ErrorResponseException {
    if (account.getTypeAccount().equalsIgnoreCase(TypeAccountBank.AHORRO.toString())
        || account.getTypeAccount().equalsIgnoreCase(TypeAccountBank.PLAZO_FIJO.toString())) {
      throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_EMPRESA_NOT_TYPE_AUTHORIZED,
          HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
    }
    if (account.getHolders() == null) {
      throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_EMPRESA_NEED_HOLDERS,
          HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
    }
  }
}
