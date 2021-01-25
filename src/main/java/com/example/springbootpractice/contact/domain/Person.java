package com.example.springbootpractice.contact.domain;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private int age;

  @NonNull
  private String hobby;

  private String bloodType;

  private String address;

  private LocalDate birthday;

  private String job;

  @ToString.Exclude
  private String phoneNumber;

}
