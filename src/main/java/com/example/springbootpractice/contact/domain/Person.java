package com.example.springbootpractice.contact.domain;

import com.example.springbootpractice.contact.domain.dto.Birthday;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@AllArgsConstructor
@Where(clause = "deleted = false")
public class Person {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  @Column(nullable = false)
  private String name;

  @NonNull
  @Column(nullable = false)
  private int age;

  private String hobby;

  private String bloodType;

  private String address;

  @Embedded
  private Birthday birthday;

  private String job;

  @ColumnDefault("0")
  private boolean deleted;

  @ToString.Exclude
  private String phoneNumber;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @ToString.Exclude
  private Block block;

  public int KoreanAge() {
    return LocalDate.now().getYear() - birthday.getYearOfBirthday() + 1;
  }

  public boolean BirthdayToday() {
    return LocalDate.now().equals(LocalDate.of(birthday.getYearOfBirthday(), birthday.getMonthOfBirthday(), birthday.getDayOfBirthday()));

  }

  public String birthdayFormat(){
    return birthday.getYearOfBirthday().toString() + "-" + birthday.getMonthOfBirthday().toString() + "-" + birthday.getDayOfBirthday().toString();
  }

}
