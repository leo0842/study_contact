package com.example.springbootpractice.contact.exception.handler;

import com.example.springbootpractice.contact.exception.PersonNotFoundException;
import com.example.springbootpractice.contact.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


  @ExceptionHandler(value = PersonNotFoundException.class)
  public ResponseEntity<ErrorResponse> PersonNotFoundException(PersonNotFoundException e) {
    return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleRuntimeException(RuntimeException e){
    log.info(e.getMessage());
    return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
  }

}
