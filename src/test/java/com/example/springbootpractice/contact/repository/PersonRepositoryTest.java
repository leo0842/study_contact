package com.example.springbootpractice.contact.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.springbootpractice.contact.domain.Block;
import com.example.springbootpractice.contact.domain.Person;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonRepositoryTest {

  @Autowired
  private PersonRepository personRepository;


  @Test
  void crud() {

    Person person = new Person();
    person.setName("Leo");
    person.setAge(20);

    personRepository.save(person);
//    System.out.println(personRepository.findAll());

    List<Person> people = personRepository.findAll();

    assertEquals(people.size(), 1);
    assertEquals(people.get(0).getId(), 1);
    assertEquals(people.get(0).getName(), "Leo");
    assertEquals(people.get(0).getAge(), 20);
  }

  @Test
  void lombokTest() {
    Person person1 = new Person();
    Person person2 = new Person();
    person1.setName("Leo");
    person2.setName("Leo");
    person1.setAge(20);
    person2.setAge(20);

    System.out.println(person1.equals(person2));
    System.out.println(person1.hashCode() == person2.hashCode());

    System.out.println(person1);

  }


  @Test
  void betweenTest() {
    savePeople();
    List<Person> people = personRepository.findByAgeBetween(21, 23);
    people.forEach(System.out::println);
  }

  private void givenPerson(String name, int age) {

    personRepository.save(new Person(name, age));
  }

  private void givenPerson2(String name, int age, String blockName) {

    Person person = new Person(name, age);
//    person.setBlock(new Block("Park"));
    Block block = new Block(blockName);
    person.setBlock(block);
    personRepository.save(person);

  }

  private void savePeople() {
    givenPerson("Lee", 20);
    givenPerson("Kim", 20);
    givenPerson("Park", 20);
    givenPerson2("Kang", 21, "Park");
    givenPerson2("Jung", 23, "Lee");
  }

}
