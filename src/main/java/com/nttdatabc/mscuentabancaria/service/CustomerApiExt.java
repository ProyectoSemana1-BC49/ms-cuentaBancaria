package com.nttdatabc.mscuentabancaria.service;

import com.nttdatabc.mscuentabancaria.model.CustomerExt;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_REQUEST;

@Service
public class CustomerApiExt {
    @Autowired
    private RestTemplate restTemplate;

    public Optional<CustomerExt> getCustomerById(String id) throws ErrorResponseException {
        String apiUrl = "http://localhost:8080/api/v1/customer/" + id;
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CustomerExt> response = restTemplate.exchange(apiUrl, HttpMethod.GET, httpEntity, CustomerExt.class);
        if(response.getStatusCode().is2xxSuccessful()){
            return Optional.ofNullable(response.getBody());
        }else{
            throw new ErrorResponseException(EX_ERROR_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }
}
