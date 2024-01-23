package com.nttdatabc.mscuentabancaria.service.strategy.strategy_typeaccount;

import com.nttdatabc.mscuentabancaria.model.Account;

/**
 * Clase contexto.
 */
public class Contexto {
  private AccountConfigationStrategy accountConfigationStrategy;

  public Contexto(AccountConfigationStrategy accountConfigationStrategy) {
    this.accountConfigationStrategy = accountConfigationStrategy;
  }

  public void configurationAccount(Account account) {
    accountConfigationStrategy.configureAccount(account);
  }
}
