package com.example.springbootpractice.contact.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Block {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  private String name;

  private String reason;

  private LocalDate startDate;

  private LocalDate endDate;



}
