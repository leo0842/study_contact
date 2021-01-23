package com.example.springbootpractice.contact.repository;

import com.example.springbootpractice.contact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {


}
