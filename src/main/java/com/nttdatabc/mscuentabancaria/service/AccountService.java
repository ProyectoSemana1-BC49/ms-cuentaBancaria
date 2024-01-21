package com.nttdatabc.mscuentabancaria.service;

import com.nttdatabc.mscuentabancaria.repository.AccountRepository;
import com.nttdatabc.mscuentabancaria.utils.Utilitarios;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.nttdatabc.mscuentabancaria.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.*;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account>getAllAccountsService(){
        return accountRepository.findAll();
    }

    public void createAccountService(Account account) throws ErrorResponseException {
        validateAccountsNoNulls(account);
        validateAccountEmpty(account);
        account.setId(Utilitarios.generateUUID());
        accountRepository.save(account);
    }
    public void updateAccountServide(Account account) throws ErrorResponseException {
        validateAccountsNoNulls(account);
        validateAccountEmpty(account);
        Optional<Account>getAccountById = accountRepository.findById(account.getId());
        if(getAccountById.isEmpty()){
            throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        }
        Account accountFound = getAccountById.get();
        accountFound.setTypeAccount(account.getTypeAccount());
        accountFound.setCurrentBalance(account.getCurrentBalance());
        accountFound.setCustomersId(account.getCustomersId());
        accountFound.setDateMovement(account.getDateMovement());
        accountFound.setLimitMaxMovements(account.getLimitMaxMovements());
        accountFound.setMaintenanceFee(account.getMaintenanceFee());
        accountRepository.save(accountFound);

    }

    public void deleteAccountByIdService(String accountId) throws ErrorResponseException {
        Optional<Account>accountFindByIdOptional = accountRepository.findById(accountId);
        if(accountFindByIdOptional.isEmpty()){
            throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        }
        accountRepository.delete(accountFindByIdOptional.get());
    }

    public Account getAccountByIdService(String accountId) throws ErrorResponseException {
        Optional<Account> customer = accountRepository.findById(accountId);
        return customer.orElseThrow(() -> new ErrorResponseException(EX_NOT_FOUND_RECURSO, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
    }

    /*====================================================================================*/
    public  void validateAccountsNoNulls(Account account) throws ErrorResponseException {
        Optional.of(account)
                .filter(c -> c.getCustomersId() != null)
                .filter(c -> c.getCurrentBalance() != null)
                .filter(c -> c.getTypeAccount() != null)
                .filter(c -> c.getDateMovement() != null)
                .filter(c -> c.getMaintenanceFee() != null)
                .filter(c -> c.getLimitMaxMovements() != null)
                .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public  void validateAccountEmpty(Account account) throws ErrorResponseException {
        Optional.of(account)
                .filter(c -> !c.getCustomersId().isEmpty())
                .filter(c -> !c.getCurrentBalance().toString().isBlank())
                .filter(c -> !c.getTypeAccount().isBlank())
                .filter(c -> !c.getDateMovement().isBlank())
                .filter(c -> !c.getMaintenanceFee().toString().isBlank())
                .filter(c -> !c.getLimitMaxMovements().toString().isBlank())
                .orElseThrow(() -> new ErrorResponseException(EX_VALUE_EMPTY,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }



}
