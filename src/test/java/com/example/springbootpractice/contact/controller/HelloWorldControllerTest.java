package com.example.springbootpractice.contact.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class HelloWorldControllerTest {

  @Autowired
  private HelloWorldController helloWorldController;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @BeforeEach
  void beforeEach() {

    mockMvc = MockMvcBuilders
        .webAppContextSetup(wac)
        .alwaysDo(print())
        .build();
  }

  @Test
  void helloWorld(){
//    System.out.println("test");
    System.out.println(helloWorldController.helloWorld());

    assertEquals(helloWorldController.helloWorld(), "HelloWorld");
  }

  @Test
  void mockMvcTest() throws Exception {

    mockMvc.perform(
        MockMvcRequestBuilders.get("/api/helloWorld")
    )
          .andExpect(status().isOk())
          .andExpect(content().string("HelloWorld"));
  }

  @Test
  void helloException() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/api/helloException")
    )
        .andExpect(status().isInternalServerError());
  }
}