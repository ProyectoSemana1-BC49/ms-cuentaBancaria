package com.nttdatabc.mscuentabancaria.service;

import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import java.util.List;

/**
 * Interface de accountService.
 */
public interface AccountService {
  List<Account> getAllAccountsService();

  void createAccountService(Account account) throws ErrorResponseException;

  void updateAccountServide(Account account) throws ErrorResponseException;

  void deleteAccountByIdService(String accountId) throws ErrorResponseException;

  Account getAccountByIdService(String accountId) throws ErrorResponseException;

  List<Account> getAccountsByCustomerIdService(String customerId) throws ErrorResponseException;
}
