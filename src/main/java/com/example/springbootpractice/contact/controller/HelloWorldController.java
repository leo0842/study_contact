package com.example.springbootpractice.contact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  @GetMapping(value = "/api/helloWorld")
  public String HelloWorld(){
    return "HelloWorld";
  }

}
