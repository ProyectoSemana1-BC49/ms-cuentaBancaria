package com.nttdatabc.mscuentabancaria.service;

import com.nttdatabc.mscuentabancaria.model.CustomerExt;
import com.nttdatabc.mscuentabancaria.model.TypeAccountBank;
import com.nttdatabc.mscuentabancaria.model.TypeCustomer;
import com.nttdatabc.mscuentabancaria.repository.AccountRepository;
import com.nttdatabc.mscuentabancaria.utils.Utilitarios;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import com.nttdatabc.mscuentabancaria.model.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.*;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerApiExt customerApiExt;


    public List<Account>getAllAccountsService(){
        return accountRepository.findAll();
    }

    public void createAccountService(Account account) throws ErrorResponseException {
        validateAccountsNoNulls(account);
        validateAccountEmpty(account);
        verifyTypeAccount(account);
        verifyValues(account);
        CustomerExt customerFound = verifyCustomerExists(account.getCustomerId());
        List<Account>listAccountByCustomer = getAccountsByCustomerIdService(account.getCustomerId());

        // TODO: Refactorizar
        if(customerFound.getType().equalsIgnoreCase(TypeCustomer.PERSONA.toString())){
            if(account.getHolders() != null){
                throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_PERSONA_NOT_HOLDERS, HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
            }
            if(listAccountByCustomer.size() >= MAX_SIZE_ACCOUNT_CUSTOMER_PERSONA){
                throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_PERSONA, HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
            }
        }else if(customerFound.getType().equalsIgnoreCase(TypeCustomer.EMPRESA.toString())){
            if(account.getTypeAccount().equalsIgnoreCase(TypeAccountBank.AHORRO.toString()) ||
            account.getTypeAccount().equalsIgnoreCase(TypeAccountBank.PLAZO_FIJO.toString())){
                throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_EMPRESA_NOT_TYPE_AUTHORIZED, HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
            }
            if(account.getHolders() == null){
                throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_EMPRESA_NEED_HOLDERS, HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
            }
        }

        //TODO: Refactorizar
        if(account.getTypeAccount().equalsIgnoreCase(TypeAccountBank.AHORRO.toString())){
            account.setMaintenanceFee(BigDecimal.valueOf(MAINTENANCE_FEE_FREE));
            account.setLimitMaxMovements(LIMIT_MAX_MOVEMENTS);
        }else if(account.getTypeAccount().equalsIgnoreCase(TypeAccountBank.CORRIENTE.toString())){
            account.setLimitMaxMovements(LIMIT_MAX_FREE);
            account.setMaintenanceFee(BigDecimal.valueOf(MAINTENANCE_FEE));
        }else if (account.getTypeAccount().equalsIgnoreCase(TypeAccountBank.PLAZO_FIJO.toString())){
            account.setMaintenanceFee(BigDecimal.valueOf(MAINTENANCE_FEE_FREE));
            account.setDateMovement(DAY_MOVEMENT_SELECTED);
        }

        account.setId(Utilitarios.generateUUID());
        accountRepository.save(account);
    }


    public void updateAccountServide(Account account) throws ErrorResponseException {
        validateAccountsNoNulls(account);
        validateAccountEmpty(account);
        verifyTypeAccount(account);
        Optional<Account>getAccountById = accountRepository.findById(account.getId());
        if(getAccountById.isEmpty()){
            throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        }
        Account accountFound = getAccountById.get();
        accountFound.setTypeAccount(account.getTypeAccount());
        accountFound.setCurrentBalance(account.getCurrentBalance());
        accountFound.setCustomerId(account.getCustomerId());
        accountFound.setHolders(account.getHolders());
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
        Optional<Account> account = accountRepository.findById(accountId);
        return account.orElseThrow(() -> new ErrorResponseException(EX_NOT_FOUND_RECURSO, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
    }

    public List<Account>getAccountsByCustomerIdService(String customerId) throws ErrorResponseException {
        verifyCustomerExists(customerId);
        return accountRepository.findByCustomerId(customerId);
    }

    /*====================================================================================*/
    public  void validateAccountsNoNulls(Account account) throws ErrorResponseException {
        Optional.of(account)
                .filter(c -> c.getCustomerId() != null)
                .filter(c -> c.getCurrentBalance() != null)
                .filter(c -> c.getTypeAccount() != null)
                .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public  void validateAccountEmpty(Account account) throws ErrorResponseException {
        Optional.of(account)
                .filter(c -> !c.getCustomerId().isEmpty())
                .filter(c -> !c.getCurrentBalance().toString().isBlank())
                .filter(c -> !c.getTypeAccount().isBlank())
                .orElseThrow(() -> new ErrorResponseException(EX_VALUE_EMPTY,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public  void verifyTypeAccount(Account account)throws ErrorResponseException{
        Predicate<Account> existTypeAccountBank = accountValidate -> accountValidate
                .getTypeAccount()
                .equalsIgnoreCase(TypeAccountBank.AHORRO.toString()) ||
                accountValidate.getTypeAccount().equalsIgnoreCase(TypeAccountBank.CORRIENTE.toString())
                || accountValidate.getTypeAccount().equalsIgnoreCase(TypeAccountBank.PLAZO_FIJO.toString());
        if(existTypeAccountBank.negate().test(account)){
            throw new ErrorResponseException(EX_ERROR_TYPE_ACCOUNT,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }
    public void verifyValues(Account account)throws ErrorResponseException{
        if(account.getCurrentBalance().doubleValue() <= VALUE_MIN_ACCOUNT_BANK){
            throw new ErrorResponseException(EX_ERROR_VALUE_MIN,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }
    public CustomerExt verifyCustomerExists(String customerId) throws ErrorResponseException {
      try{
          Optional<CustomerExt>customerExtOptional = customerApiExt.getCustomerById(customerId);
          return customerExtOptional.get();
      }catch (Exception e){
          throw new ErrorResponseException(EX_NOT_FOUND_RECURSO, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
      }
    }



}
