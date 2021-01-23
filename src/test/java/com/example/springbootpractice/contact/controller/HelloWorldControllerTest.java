package com.example.springbootpractice.contact.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class HelloWorldControllerTest {

  @Autowired
  private HelloWorldController helloWorldController;

  private MockMvc mockMvc;

  @Test
  void helloWorld(){
//    System.out.println("test");
    System.out.println(helloWorldController.HelloWorld());

    Assertions.assertEquals(helloWorldController.HelloWorld(), "HelloWorld");
  }

  @Test
  void mockMvcTest() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();

    mockMvc.perform(
        MockMvcRequestBuilders.get("/api/helloWorld")
    )
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string("HelloWorld"));
  }
}