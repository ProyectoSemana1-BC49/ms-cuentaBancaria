package com.nttdatabc.mscuentabancaria.utils;


/**
 * Clase constante.
 */
public class Constantes {
  public static final String PREFIX_PATH = "/api/v1";
  public static final String EX_ERROR_REQUEST = "Error en uno de los parámetros";
  public static final String EX_ERROR_TYPE_ACCOUNT = "Recuerda que solo existe tipo AHORRO | CORRIENTE | PLAZO_FIJO";
  public static final String EX_VALUE_EMPTY = "Uno de los parámetros viene vacío";
  public static final String EX_NOT_FOUND_RECURSO = "No existe el recurso";
  public static final String EX_ERROR_VALUE_MIN = "El valor es el mínimo para abrir un producto bancario";
  public static final String EX_ERROR_VALUE_MIN_MOVEMENT = "El valor es el mínimo para realizar un movimiento";
  public static final String EX_ERROR_MOVEMENT_BALANCE_INSUFFICIENT = "Saldo insuficiente";
  public static Double VALUE_MIN_ACCOUNT_BANK = 0.0;
  public static final Double MAINTENANCE_FEE = 12.5;
  public static final Double MAINTENANCE_FEE_FREE = 0.0;
  public static final Integer LIMIT_MAX_MOVEMENTS = 5;
  public static final Integer LIMIT_MAX_FREE = 0;
  public static final String DAY_MOVEMENT_SELECTED = "22";
  public static final String EX_ERROR_TYPE_MOVEMENT = "Recuerda que solo existe el tipo RETIRO | DEPOSITO";
  public static final String EX_ERROR_CONFLICTO_CUSTOMER_PERSONA = "Este usuario ya tiene registrado alguna cuenta bancaria.";
  public static final String EX_ERROR_CONFLICTO_CUSTOMER_PERSONA_NOT_HOLDERS = "Las cuentas personales, no pueden tener Holders";
  public static final String EX_ERROR_CONFLICTO_CUSTOMER_EMPRESA_NOT_TYPE_AUTHORIZED = "Las cuentas de empresa, no pueden ser de ahorro o de plazo fijo";
  public static final String EX_ERROR_CONFLICTO_CUSTOMER_EMPRESA_NEED_HOLDERS = "Las cuentas de empresa, necesita al menos 1 holder";
  public static final Integer MAX_SIZE_ACCOUNT_CUSTOMER_PERSONA = 1;
  public static final String EX_ERROR_LIMIT_MAX_MOVEMENTS = "LA CUENTA DE AHORRO TIENE UN MÁXIMO DE 5 MOVIMIENTOS MENSUALES, ESPERE AL OTRO MES PARA REALIZAR MOVIMIENTO";
  public static final String EX_ERROR_HAS_MOVEMENT_DAY = "Ya realizó el movimiento, recuerde que solo es 1 por día específico.";
  public static final String EX_ERROR_NOT_DAY_MOVEMENT = "Hoy no es el día especificado para hacer un movimiento en plazo fijo, recurde que son los días " + DAY_MOVEMENT_SELECTED + " de cada mes.";
  public static final String URL_CUSTOMER_ID = "http://localhost:8080/api/v1/customer/";
}
