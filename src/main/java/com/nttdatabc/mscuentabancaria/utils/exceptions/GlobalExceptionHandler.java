package com.nttdatabc.mscuentabancaria.utils.exceptions;


import com.nttdatabc.mscuentabancaria.utils.exceptions.dto.ErrorDTO;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ErrorResponseException.class)
    public ResponseEntity<ErrorDTO> handleCustomException(ErrorResponseException e){

        ErrorDTO error = ErrorDTO.builder()
                .httpStatus(e.getHttpStatus())
                .message(e.getMessage())
                .code(e.getStatus())
                .build();
        return new ResponseEntity<ErrorDTO>(error, e.getHttpStatus());

    }
}