package com.example.springbootpractice.contact.repository;

import com.example.springbootpractice.contact.domain.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Long> {

  List<Person> findByName(String name);

  List<Person> findByBlockIsNotNull();

  List<Person> findByAgeBetween(int from, int to);

  @Query(value = "select * from person where deleted = true", nativeQuery = true)
  List<Person> findPeopleDeleted();

}
