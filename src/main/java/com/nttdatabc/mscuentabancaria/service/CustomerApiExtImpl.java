package com.nttdatabc.mscuentabancaria.service;

import static com.nttdatabc.mscuentabancaria.utils.Constantes.EX_ERROR_REQUEST;
import static com.nttdatabc.mscuentabancaria.utils.Constantes.URL_CUSTOMER_ID;

import com.nttdatabc.mscuentabancaria.model.CustomerExt;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Clase service.
 */
@Service
public class CustomerApiExtImpl implements CustomerApiExt {
  @Autowired
  private RestTemplate restTemplate;

  @Override
  public Optional<CustomerExt> getCustomerById(String id) throws ErrorResponseException {
    String apiUrl = URL_CUSTOMER_ID + id;
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<CustomerExt> response = restTemplate.exchange(apiUrl, HttpMethod.GET, httpEntity, CustomerExt.class);
    if (response.getStatusCode().is2xxSuccessful()) {
      return Optional.ofNullable(response.getBody());
    } else {
      throw new ErrorResponseException(EX_ERROR_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }
  }
}
