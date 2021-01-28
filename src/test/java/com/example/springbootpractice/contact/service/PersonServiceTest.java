package com.example.springbootpractice.contact.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.springbootpractice.contact.domain.Block;
import com.example.springbootpractice.contact.domain.Person;
import com.example.springbootpractice.contact.repository.BlockRepository;
import com.example.springbootpractice.contact.repository.PersonRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PersonServiceTest {

  @Autowired
  private PersonService personService;

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private BlockRepository blockRepository;

  @Test
  void getPeopleExcludeBlocks(){
    //Relation 설정
//    givenPeople();

    //Relation 설정 후
    givenPeopleByRelation();
  }

  @Test
  @Transactional
  void cascadeTest(){
    givenPerson("Lee",20);
    givenPerson("Kim",20);
    givenPerson("Park",20);
    givenPerson2("Kang",21);
    List<Person> people = personRepository.findAll();
    System.out.println(blockRepository.findAll());
    people.forEach(System.out::println);
    Person person = people.get(3);
    person.getBlock().setStartDate(LocalDate.now());
    people.forEach(System.out::println);
    personRepository.delete(person);

    System.out.println("####");
    people.forEach(System.out::println);
    System.out.println("####");
    personRepository.findAll().forEach(System.out::println);
    System.out.println("####");
    blockRepository.findAll().forEach(System.out::println);

  }

  @Test
  void jpaQueryMethod(){

    savePeople();
    List<Person> people = personService.getPeopleByName("Lee");
    System.out.println(people);
    System.out.println("============");
    List<Person> people1 = personService.getPeopleByNameWithRepo("Lee");
    System.out.println(people1);
    List<Person> people2 = personService.getPeopleByBlock();
    System.out.println(people2);
  }

  @Test
  void getPerson(){
    givenPerson2("Lee",20);
    System.out.println(personService.getPerson(1L));
  }

  private void givenPeopleByRelation() {
    givenPerson("Lee",20);
    givenPerson("Kim",20);
    givenPerson("Park",20);
    //givenPerson("Choi",21, givenBlock("Park"));

    List<Person> people = personService.getPeopleExcludeBlockByRelation();
    people.forEach(System.out::println);

//    personService.check();
  }

  private void givenPeople(){
    givenPerson("Lee",20);
    givenPerson("Kim",20);
    givenPerson("Park",20);
    givenPerson("Choi",21);
    givenBlock("Park");

    List<Person> people = personService.getPeopleExcludeBlock();
    people.forEach(System.out::println);

  }

  private Block givenBlock(String name) {

    return blockRepository.save(new Block(name));

  }

  private void givenPerson(String name, int age) {

    personRepository.save(new Person(name,age));
  }

  private void givenPerson2(String name, int age) {

    Person person = new Person(name,age);
//    person.setBlock(new Block("Park"));
    Block block = new Block("Park");
    person.setBlock(block);
    personRepository.save(person);

  }

  private void savePeople(){
    givenPerson("Lee",20);
    givenPerson("Kim",20);
    givenPerson("Park",20);
    givenPerson("Kang",21);
    givenPerson2("Jung",23);
  }

}