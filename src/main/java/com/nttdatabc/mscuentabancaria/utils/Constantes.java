package com.nttdatabc.mscuentabancaria.utils;

import java.math.BigDecimal;

public class Constantes {
    public static final String PREFIX_PATH= "/api/v1";
    public static final String EX_ERROR_REQUEST = "Error en uno de los parámetros";
    public static final String EX_ERROR_TYPE_ACCOUNT = "Recuerda que solo existe tipo AHORRO | CORRIENTE | PLAZO_FIJO";
    public static final String EX_VALUE_EMPTY = "Uno de los parámetros viene vacío";
    public static final String EX_NOT_FOUND_RECURSO = "No existe el recurso";
    public static final String EX_USER_REGISTRED = "Este documento ya ha sido registrado.";
    public static final String EX_ERROR_FORMAT_NUMBER_ID = "El valor no es numérico";
    public static final String EX_ERROR_VALUE_MIN= "El valor es el mínimo para abrir un producto bancario";
    public static final String EX_ERROR_PERSONA_AUTHORIZED_SIGNER = "El Tipo persona no puede tener Singatarios";
    public static Double VALUE_MIN_ACCOUNT_BANK = 0.0;
    public static final Double MAINTENANCE_FEE = 12.5;
    public static final Integer LIMIT_MAX_MOVEMENTS = 5;
    public static final String EX_ERROR_TYPE_MOVEMENT = "Recuerda que solo existe el tipo RETIRO | DEPOSITO";
}
