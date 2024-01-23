package com.nttdatabc.mscuentabancaria.service.strategy.strategy_account;

import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import java.util.List;

/**
 * Clase contexto.
 */
public class Contexto {
  private AccountValidationStrategy accountValidationStrategy;

  public Contexto(AccountValidationStrategy accountValidationStrategy) {
    this.accountValidationStrategy = accountValidationStrategy;
  }

  public void validate(Account account, List<Account> accountList) throws ErrorResponseException {
    this.accountValidationStrategy.validateAccount(account, accountList);
  }
}
