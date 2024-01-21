package com.nttdatabc.mscuentabancaria.controller;

import com.nttdatabc.mscuentabancaria.service.AccountService;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.nttdatabc.mscuentabancaria.api.AccountsApi;
import org.nttdatabc.mscuentabancaria.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.PREFIX_PATH;

@RestController
@RequestMapping(PREFIX_PATH)
public class AccountController implements AccountsApi {

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccountsService(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createAccount(Account account) {
        try {
            accountService.createAccountService(account);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateAccount(Account account) {
        try {
            accountService.updateAccountServide(account);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteAccountById(String accountId) {
        try {
            accountService.deleteAccountByIdService(accountId);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Account> getAccountById(String accountId) {
        Account accountById = null;
        try {
            accountById = accountService.getAccountByIdService(accountId);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(accountById, HttpStatus.OK);
    }
}
