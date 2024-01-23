package com.nttdatabc.mscuentabancaria.utils;

import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.model.CustomerExt;
import com.nttdatabc.mscuentabancaria.model.TypeAccountBank;
import com.nttdatabc.mscuentabancaria.service.CustomerApiExtImpl;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.*;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_NOT_FOUND_RECURSO;

public class AccountValidator {
    public static void validateAccountsNoNulls(Account account) throws ErrorResponseException {
        Optional.of(account)
                .filter(c -> c.getCustomerId() != null)
                .filter(c -> c.getCurrentBalance() != null)
                .filter(c -> c.getTypeAccount() != null)
                .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public static void validateAccountEmpty(Account account) throws ErrorResponseException {
        Optional.of(account)
                .filter(c -> !c.getCustomerId().isEmpty())
                .filter(c -> !c.getCurrentBalance().toString().isBlank())
                .filter(c -> !c.getTypeAccount().isBlank())
                .orElseThrow(() -> new ErrorResponseException(EX_VALUE_EMPTY,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public static void verifyTypeAccount(Account account)throws ErrorResponseException{
        Predicate<Account> existTypeAccountBank = accountValidate -> accountValidate
                .getTypeAccount()
                .equalsIgnoreCase(TypeAccountBank.AHORRO.toString()) ||
                accountValidate.getTypeAccount().equalsIgnoreCase(TypeAccountBank.CORRIENTE.toString())
                || accountValidate.getTypeAccount().equalsIgnoreCase(TypeAccountBank.PLAZO_FIJO.toString());
        if(existTypeAccountBank.negate().test(account)){
            throw new ErrorResponseException(EX_ERROR_TYPE_ACCOUNT,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }
    public static void verifyValues(Account account)throws ErrorResponseException{
        if(account.getCurrentBalance().doubleValue() <= VALUE_MIN_ACCOUNT_BANK){
            throw new ErrorResponseException(EX_ERROR_VALUE_MIN,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }
    public static CustomerExt verifyCustomerExists(String customerId, CustomerApiExtImpl customerApiExtImpl) throws ErrorResponseException {
        try{
            Optional<CustomerExt>customerExtOptional = customerApiExtImpl.getCustomerById(customerId);
            return customerExtOptional.get();
        }catch (Exception e){
            throw new ErrorResponseException(EX_NOT_FOUND_RECURSO, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        }
    }
}
