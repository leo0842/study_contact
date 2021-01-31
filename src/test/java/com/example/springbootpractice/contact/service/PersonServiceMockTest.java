package com.example.springbootpractice.contact.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springbootpractice.contact.domain.Person;
import com.example.springbootpractice.contact.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class PersonServiceMockTest {
  @InjectMocks
  private PersonService personService;

  @Mock
  private PersonRepository personRepository;

  @Test
  void getPeopleByName(){
    when(personRepository.findByName("Leo")) //findByName 이라는 메소드가 호출될 때
        .thenReturn(Lists.newArrayList(new Person("Leo",23))); // Person new Person("Leo",23)이 리턴될 것이다.

//    System.out.println(personRepository.findAll()); //없다
//    System.out.println(personRepository.findByName("Leo")); //있다
//    System.out.println(personRepository.findAll()); //없다

    List<Person> result = personService.getPeopleByName("Leo"); //getPeopleByName 메소드에 findByName이 있다.
    Assertions.assertEquals(1, result.size());
  }

  @Test
  void getPerson(){
    when(personRepository.findById(1L))
        .thenReturn(Optional.of(new Person("Leo", 23)));

    Person person = personService.getPerson(1L);

    Assertions.assertEquals(person.getName(), "Leo");

  }

  @Test
  void getPersonNotFound(){
    when(personRepository.findById(1L))
        .thenReturn(Optional.empty());
    Person person = personService.getPerson(1L);

    Assertions.assertNull(person);
  }

  @Test
  void savePerson() {
    Person person = new Person("Leo", 23);
//    when(personRepository.save(person))
//        .thenReturn(person);
    personService.savePerson(person);
//
//    Assertions.assertEquals(person.getName(), person1.getName());

    verify(personRepository).save(any(Person.class));
  }

  @Test
  void updatePerson() {
    when(personRepository.findById(1L))
        .thenReturn(Optional.of(new Person("Leo", 23)));

    personService.updatePerson(1L, new Person("Leo", 23));

    Assertions.assertEquals(personRepository.findById(1L).get().getName(), "Leo");
  }
}