package com.nttdatabc.mscuentabancaria.utils.exceptions;


import com.nttdatabc.mscuentabancaria.utils.exceptions.dto.ErrorDto;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase que maneja excepciones globales para proporcionar respuestas uniformes.
 * Anotada con @RestControllerAdvice para gestionar excepciones en todos los controladores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  /**
   * Maneja excepciones personalizadas del tipo ErrorResponseException.
   *
   * @param e Excepción personalizada a manejar.
   * @return ResponseEntity con información de error en formato ErrorDto.
   */
  @ExceptionHandler(value = ErrorResponseException.class)
  public ResponseEntity<ErrorDto> handleCustomException(ErrorResponseException e) {

    ErrorDto error = ErrorDto.builder()
        .httpStatus(e.getHttpStatus())
        .message(e.getMessage())
        .code(e.getStatus())
        .build();
    return new ResponseEntity<ErrorDto>(error, e.getHttpStatus());

  }
}
