package com.nttdatabc.mscuentabancaria.utils;


import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_REQUEST;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_TYPE_ACCOUNT;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_VALUE_MIN;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_NOT_FOUND_RECURSO;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_VALUE_EMPTY;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.VALUE_MIN_ACCOUNT_BANK;

import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.model.CustomerExt;
import com.nttdatabc.mscuentabancaria.model.TypeAccountBank;
import com.nttdatabc.mscuentabancaria.service.CustomerApiExtImpl;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.http.HttpStatus;

/**
 * Class.
 */
public class AccountValidator {
  /**
   * Valida que los campos esenciales de la cuenta no sean nulos.
   *
   * @param account La cuenta que se va a validar.
   * @throws ErrorResponseException Si algún campo esencial es nulo.
   */
  public static void validateAccountsNoNulls(Account account) throws ErrorResponseException {
    Optional.of(account)
        .filter(c -> c.getCustomerId() != null)
        .filter(c -> c.getCurrentBalance() != null)
        .filter(c -> c.getTypeAccount() != null)
        .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST,
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
  }

  /**
   * Valida que los campos de la cuenta no estén vacíos.
   *
   * @param account La cuenta que se va a validar.
   * @throws ErrorResponseException Si algún campo esencial está vacío.
   */
  public static void validateAccountEmpty(Account account) throws ErrorResponseException {
    Optional.of(account)
        .filter(c -> !c.getCustomerId().isEmpty())
        .filter(c -> !c.getCurrentBalance().toString().isBlank())
        .filter(c -> !c.getTypeAccount().isBlank())
        .orElseThrow(() -> new ErrorResponseException(EX_VALUE_EMPTY,
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
  }

  /**
   * Verifica que el tipo de cuenta sea válido.
   *
   * @param account La cuenta que se va a verificar.
   * @throws ErrorResponseException Si el tipo de cuenta no es válido.
   */
  public static void verifyTypeAccount(Account account) throws ErrorResponseException {
    Predicate<Account> existTypeAccountBank = accountValidate -> accountValidate
        .getTypeAccount()
        .equalsIgnoreCase(TypeAccountBank.AHORRO.toString())
        || accountValidate.getTypeAccount().equalsIgnoreCase(TypeAccountBank.CORRIENTE.toString())
        || accountValidate.getTypeAccount().equalsIgnoreCase(TypeAccountBank.PLAZO_FIJO.toString());
    if (existTypeAccountBank.negate().test(account)) {
      throw new ErrorResponseException(EX_ERROR_TYPE_ACCOUNT,
          HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Verifica que los valores de la cuenta sean válidos.
   *
   * @param account La cuenta que se va a verificar.
   * @throws ErrorResponseException Si los valores no son válidos.
   */
  public static void verifyValues(Account account) throws ErrorResponseException {
    if (account.getCurrentBalance().doubleValue() <= VALUE_MIN_ACCOUNT_BANK) {
      throw new ErrorResponseException(EX_ERROR_VALUE_MIN,
          HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Verifica la existencia de un cliente mediante su ID.
   *
   * @param customerId         El ID del cliente.
   * @param customerApiExtImpl Implementación de la interfaz CustomerApiExt.
   * @return La información del cliente si existe.
   * @throws ErrorResponseException Si el cliente no existe.
   */
  public static CustomerExt verifyCustomerExists(String customerId, CustomerApiExtImpl customerApiExtImpl) throws ErrorResponseException {
    try {
      Optional<CustomerExt> customerExtOptional = customerApiExtImpl.getCustomerById(customerId);
      return customerExtOptional.get();
    } catch (Exception e) {
      throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,
          HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
    }
  }
}
