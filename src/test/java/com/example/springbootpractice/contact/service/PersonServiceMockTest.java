package com.example.springbootpractice.contact.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
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
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  void getPeople(){
    when(personRepository.findAll(any(Pageable.class)))
        .thenReturn(new PageImpl<>(Lists.newArrayList(new Person("Leo", 23), new Person("Kim", 25))));

    Page<Person> people = personService.getPeople(PageRequest.of(0, 3));

    Assertions.assertEquals(people.getNumberOfElements(), 2);
    assertEquals(people.getContent().get(0).getName(), "Leo");
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
  void updatePersonNotFound() {
    when(personRepository.findById(1L))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> personService.updatePerson(1L, new Person("Leo", 23)));

  }

  @Test
  void updatePerson() {
    Person person = new Person("Leo", 23);
    when(personRepository.findById(1L))
        .thenReturn(Optional.of(person));
    person.setName("updated Leo");
    personService.updatePerson(1L, person);

//    verify(personRepository).save(any(Person.class));
    verify(personRepository).save(argThat(new IsPersonUpdated()));
  }

  @Test
  void deletePersonNotFound() {
    when(personRepository.findById(1L))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> personService.deletePerson(1L));
  }

  @Test
  void deletePerson() {
    when(personRepository.findById(1L))
        .thenReturn(Optional.of(new Person("Leo", 23)));
    personService.deletePerson(1L);
    verify(personRepository).save(argThat(new IsPersonDeleted()));
  }

  private static class IsPersonUpdated implements ArgumentMatcher<Person> {

    @Override
    public boolean matches(Person person) {
      return person.getName().equals("updated Leo");
    }
  }

  private static class IsPersonDeleted implements ArgumentMatcher<Person> {

    @Override
    public boolean matches(Person person) {
      return person.isDeleted();
    }
  }
}