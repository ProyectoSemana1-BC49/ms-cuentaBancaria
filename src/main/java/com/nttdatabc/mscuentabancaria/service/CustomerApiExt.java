package com.nttdatabc.mscuentabancaria.service;

import com.nttdatabc.mscuentabancaria.model.CustomerExt;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;

import java.util.Optional;

public interface CustomerApiExt {
    Optional<CustomerExt> getCustomerById(String id) throws ErrorResponseException;
}
