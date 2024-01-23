package com.nttdatabc.mscuentabancaria.service.strategy.strategy_typeaccount;

import com.nttdatabc.mscuentabancaria.model.Account;

/**
 * Configura la cuenta según la estrategia específica implementada.
 *
 * @param account La cuenta que se va a configurar.
 */
public interface AccountConfigationStrategy {
  void configureAccount(Account account);
}
