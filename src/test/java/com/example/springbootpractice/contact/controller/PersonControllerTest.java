package com.example.springbootpractice.contact.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootpractice.contact.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class PersonControllerTest {

  @Autowired
  private PersonController personController;

  @Autowired
  private PersonRepository personRepository;

  private MockMvc mockMvc;

  @BeforeEach
  void beforeEach() {
    mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
  }

  @Test
  void getPerson() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/api/person").param("id", "3")
    ).andExpect(status().isOk());
  }

  //  @Test
//  void getPeople() throws Exception {
//    mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
//    mockMvc.perform(
//        MockMvcRequestBuilders.get("/api/person/list")
//    ).andExpect(status().isOk());
//  }
  @Test
  void updatePerson() throws Exception {

    mockMvc.perform(
        MockMvcRequestBuilders.put("/api/person/3")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\n"
                    + "  \"name\": \"Park\"\n"
                    + "}"))
        .andExpect(status().isOk());
  }

  @Test
  void deletePerson() throws Exception {

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/api/person/4")
    ).andExpect(status().isOk());

    System.out.println(personRepository.findPeopleDeleted());
  }


}