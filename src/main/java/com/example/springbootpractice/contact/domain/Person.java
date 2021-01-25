package com.example.springbootpractice.contact.domain;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Person {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  private String name;

  @NonNull
  private int age;

  private String hobby;

  private String bloodType;

  private String address;

  private LocalDate birthday;

  private String job;

  @ToString.Exclude
  private String phoneNumber;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @ToString.Exclude
  private Block block;

}
