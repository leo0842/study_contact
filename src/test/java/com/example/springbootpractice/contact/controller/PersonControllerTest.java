package com.example.springbootpractice.contact.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootpractice.contact.domain.Person;
import com.example.springbootpractice.contact.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class PersonControllerTest {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private ObjectMapper objectMapper;

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
  void getPerson() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/api/person").param("id", "2")
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Leo"))
        .andExpect(jsonPath("$.birthday").value("1993-10-19"));
  }

  @Test
  void getPeople() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/api/person/list")
            .param("page","0")
            .param("size","1")
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.[0].name").value("Leo"));
  }

  //  @Test
//  void getPeople() throws Exception {
//    mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
//    mockMvc.perform(
//        MockMvcRequestBuilders.get("/api/person/list")
//    ).andExpect(status().isOk());
//  }
  @Test
  @Transactional
  void savePerson() throws Exception {
    Person person = new Person();
    person.setName("Leo Kim");
    mockMvc.perform(
        MockMvcRequestBuilders.post("/api/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(person)))
        .andExpect(status().isCreated());
    personRepository.findAll().forEach(System.out::println);
    Assertions.assertEquals(personRepository.findById(1L).get().getName(), "Leo Kim");
  }

  @Test
  @Transactional
  void updatePerson() throws Exception {

    Person person = new Person();
    person.setId(2L);
    person.setName("Park");

    System.out.println(personRepository.findById(2L).get().getName());
    mockMvc.perform(
        MockMvcRequestBuilders.put("/api/person/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(person)))
        .andExpect(status().isOk());

    Assertions.assertAll(
        () -> Assertions.assertEquals(personRepository.findById(2L).get().getName(), "Park"),
        () -> Assertions.assertEquals(personRepository.findById(2L).get().getAge(), 21)
    );

  }
  @Test
  @Transactional
  void deletePerson() throws Exception {

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/api/person/2")
    )
        .andExpect(status().isOk());

    Assertions.assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(2L)));
  }

  @Test
  @Transactional
  void deletePersonNotFound() throws Exception {

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/api/person/4")
    )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(400))
        .andExpect(jsonPath("$.message").value("No data"));

    Assertions.assertFalse(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(4L)));
  }

  @Test
  void checkObjectMapper() throws JsonProcessingException {
    Person person = new Person();
    person.setName("Park");
    System.out.println(">> " + toJsonString(person));
  }

  private String toJsonString(Person person) throws JsonProcessingException {
    return objectMapper.writeValueAsString(person);
  }

}