package com.nttdatabc.mscuentabancaria.service.strategy.strategy_typeaccount;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.LIMIT_MAX_MOVEMENTS;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.MAINTENANCE_FEE_FREE;

import com.nttdatabc.mscuentabancaria.model.Account;
import java.math.BigDecimal;

/**
 * Clase Strategy.
 */
public class AhorroAccountConfigurationStrategy implements AccountConfigationStrategy {
  @Override
  public void configureAccount(Account account) {
    account.setMaintenanceFee(BigDecimal.valueOf(MAINTENANCE_FEE_FREE));
    account.setLimitMaxMovements(LIMIT_MAX_MOVEMENTS);
  }
}
