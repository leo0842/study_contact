package com.example.springbootpractice.contact.service;

import com.example.springbootpractice.contact.domain.Block;
import com.example.springbootpractice.contact.domain.Person;
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

  public List<Person> getPeopleExcludeBlock(){
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

  public List<Person> getPeopleExcludeBlockByRelation(){
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
  public Person getPerson(Long id){
    Optional<Person> person = personRepository.findById(id);
    log.info("person: {}", person.orElse(null));
    return person.get();
  }
}
