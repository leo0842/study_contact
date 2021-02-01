package com.example.springbootpractice.contact.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonNotFoundException extends RuntimeException{

  public PersonNotFoundException(String message) {
    super(message);
    log.error(message);
  }

}
