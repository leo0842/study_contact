package com.example.springbootpractice.contact.service;

import com.example.springbootpractice.contact.domain.Block;
import com.example.springbootpractice.contact.domain.Person;
import com.example.springbootpractice.contact.exception.PersonNotFoundException;
import com.example.springbootpractice.contact.repository.BlockRepository;
import com.example.springbootpractice.contact.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private BlockRepository blockRepository;

  public List<Person> getPeopleByName(String name) {
//    List<Person> people = personRepository.findAll();
//    return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());

    return personRepository.findByName(name);

  }

  public List<Person> getPeopleByNameWithRepo(String name) {
    return personRepository.findByName(name);
  }

  public List<Person> getPeopleByBlock() {
    return personRepository.findByBlockIsNotNull();
  }

  public List<Person> getPeopleExcludeBlock() {
    List<Person> people = personRepository.findAll();
    return people
        .stream().filter(person ->
            !blockRepository.findAll()
                .stream()
                .map(Block::getName)
                .collect(Collectors.toList())
                .contains(person.getName()))
        .collect(Collectors.toList());
  }

  public List<Person> getPeopleExcludeBlockByRelation() {
    List<Person> people = personRepository.findAll();

    List<String> blockedPeople = people
        .stream()
        .filter(person -> person.getBlock() != null)
        .map(person -> person.getBlock().getName())
        .collect(Collectors.toList());

    return people
        .stream()
        .filter(person -> !blockedPeople
            .contains(person.getName()))
        .collect(Collectors.toList());

  }

  //  @Transactional(readOnly = true)
  public Person getPerson(Long id) {
    Optional<Person> person = personRepository.findById(id);
    Person person1 = person.orElse(null);
    if (person1 != null) {
      if (person1.getBirthday() != null) {
        log.info("person: {}", person1.birthdayFormat());
      }
    }
    return person.orElse(null);
  }

  @Transactional
  public void savePerson(Person person) {
    System.out.println("In PersonService: " + person);
    personRepository.save(person);
  }

  @Transactional
  public void updatePerson(Long id, Person person) {
    Person person1 = personRepository.findById(id).orElseThrow(() -> new RuntimeException("No data"));
    person1.setName(person.getName());
    person1.setHobby(person.getHobby());
    person1.setBloodType(person.getBloodType());
    personRepository.save(person1);
  }

  @Transactional
  public void deletePerson(Long id) {
    Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("No data"));
    person.setDeleted(true);
    personRepository.save(person);
  }

//  @Transactional
//  public List<Person> getPeople() {
//    List<Person> people = personRepository.findAll();
//    System.out.println(people);
//    return people;
//  }
}
