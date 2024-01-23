package com.nttdatabc.mscuentabancaria.service.strategy.strategy_typeaccount;

import com.nttdatabc.mscuentabancaria.model.Account;

import java.math.BigDecimal;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.LIMIT_MAX_MOVEMENTS;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.MAINTENANCE_FEE_FREE;

public class AhorroAccountConfigurationStrategy implements AccountConfigationStrategy{
    @Override
    public void configureAccount(Account account) {
        account.setMaintenanceFee(BigDecimal.valueOf(MAINTENANCE_FEE_FREE));
        account.setLimitMaxMovements(LIMIT_MAX_MOVEMENTS);
    }
}
