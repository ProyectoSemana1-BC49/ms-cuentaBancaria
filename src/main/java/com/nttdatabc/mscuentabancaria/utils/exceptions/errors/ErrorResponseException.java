package com.nttdatabc.mscuentabancaria.utils.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@Data
public class ErrorResponseException extends Exception {
    HttpStatus httpStatus;
    int status;
    public ErrorResponseException(String messaage, int status, HttpStatus httpStatus){
        super(messaage);
        this.httpStatus = httpStatus;
        this.status = status;
    }
}
