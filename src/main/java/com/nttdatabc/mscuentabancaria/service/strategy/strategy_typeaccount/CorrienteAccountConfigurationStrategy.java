package com.nttdatabc.mscuentabancaria.service.strategy.strategy_typeaccount;

import com.nttdatabc.mscuentabancaria.model.Account;

import java.math.BigDecimal;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.LIMIT_MAX_FREE;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.MAINTENANCE_FEE;

public class CorrienteAccountConfigurationStrategy implements AccountConfigationStrategy{
    @Override
    public void configureAccount(Account account) {
        account.setLimitMaxMovements(LIMIT_MAX_FREE);
        account.setMaintenanceFee(BigDecimal.valueOf(MAINTENANCE_FEE));
    }
}
