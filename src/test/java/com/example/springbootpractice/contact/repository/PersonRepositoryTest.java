package com.example.springbootpractice.contact.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.springbootpractice.contact.domain.Person;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonRepositoryTest {

  @Autowired
  private PersonRepository personRepository;


  @Test
  void crud(){

    Person person = new Person();
    person.setName("Leo");
    person.setAge(20);

    personRepository.save(person);
//    System.out.println(personRepository.findAll());

    List<Person> people = personRepository.findAll();

    assertEquals(people.size(),1);
    assertEquals(people.get(0).getId(),1);
    assertEquals(people.get(0).getName(),"Leo");
    assertEquals(people.get(0).getAge(),20);
  }

}