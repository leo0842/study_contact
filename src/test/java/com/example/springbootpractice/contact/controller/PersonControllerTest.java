package com.example.springbootpractice.contact.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootpractice.contact.domain.Person;
import com.example.springbootpractice.contact.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PersonControllerTest {

  @Autowired
  private PersonController personController;

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MappingJackson2HttpMessageConverter messageConverter;

  private MockMvc mockMvc;

  @BeforeEach
  void beforeEach() {
    mockMvc = MockMvcBuilders.standaloneSetup(personController).setMessageConverters(messageConverter).build();
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

    System.out.println(personRepository.findAll());

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/api/person/2")
    )
        .andExpect(status().isOk());
//        .andExpect(content().string("true"));
//

    Assertions.assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(2L)));
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