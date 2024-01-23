package com.nttdatabc.mscuentabancaria.service.strategy.strategy_typeaccount;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.DAY_MOVEMENT_SELECTED;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.MAINTENANCE_FEE_FREE;

import com.nttdatabc.mscuentabancaria.model.Account;
import java.math.BigDecimal;

/**
 * Clase configuration.
 */
public class PlazoFijoAccountConfigurationStrategy implements AccountConfigationStrategy {
  @Override
  public void configureAccount(Account account) {
    account.setMaintenanceFee(BigDecimal.valueOf(MAINTENANCE_FEE_FREE));
    account.setDateMovement(DAY_MOVEMENT_SELECTED);
  }
}
