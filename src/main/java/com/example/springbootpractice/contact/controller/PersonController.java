package com.example.springbootpractice.contact.controller;

import com.example.springbootpractice.contact.domain.Person;
import com.example.springbootpractice.contact.repository.PersonRepository;
import com.example.springbootpractice.contact.service.PersonService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/person")
@Slf4j
public class PersonController {

  @Autowired
  private PersonService personService;

  @GetMapping
  public Person getPerson(@RequestParam Long id){

    return personService.getPerson(id);

  }

  @GetMapping("/list")
  public Page<Person> getPeople(@PageableDefault Pageable pageable){
    return personService.getPeople(pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void postPerson(@RequestBody Person person){
    personService.savePerson(person);
  }

  @PutMapping(value = "/{id}")
  public void updatePerson(@PathVariable Long id, @RequestBody Person person){
    personService.updatePerson(id, person);
  }

  @DeleteMapping(value = "/{id}")
  public void deletePerson(@PathVariable Long id) {
    personService.deletePerson(id);
  }

}
