package com.nttdatabc.mscuentabancaria.controller;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.PREFIX_PATH;

import com.nttdatabc.mscuentabancaria.api.AccountsApi;
import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.service.AccountServiceImpl;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador de Account.
 */
@RestController
@RequestMapping(PREFIX_PATH)
public class AccountController implements AccountsApi {

  @Autowired
  private AccountServiceImpl accountServiceImpl;

  @Override
  public ResponseEntity<List<Account>> getAllAccounts() {
    return new ResponseEntity<>(accountServiceImpl.getAllAccountsService(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> createAccount(Account account) {
    try {
      accountServiceImpl.createAccountService(account);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<Void>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Void> updateAccount(Account account) {
    try {
      accountServiceImpl.updateAccountServide(account);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> deleteAccountById(String accountId) {
    try {
      accountServiceImpl.deleteAccountByIdService(accountId);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Account> getAccountById(String accountId) {
    Account accountById = null;
    try {
      accountById = accountServiceImpl.getAccountByIdService(accountId);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<>(accountById, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<Account>> getAccountsByCustomerId(String customerId) {
    List<Account> listAccountByCustomer = null;
    try {
      listAccountByCustomer = accountServiceImpl.getAccountsByCustomerIdService(customerId);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<>(listAccountByCustomer, HttpStatus.OK);
  }
}
