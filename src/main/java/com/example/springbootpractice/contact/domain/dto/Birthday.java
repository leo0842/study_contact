package com.example.springbootpractice.contact.domain.dto;

import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
@Access(AccessType.FIELD)
public class Birthday {

  private Integer yearOfBirthday;
  private Integer monthOfBirthday;
  private Integer dayOfBirthday;

  public Birthday(LocalDate birthday) {
    yearOfBirthday = birthday.getYear();
    monthOfBirthday = birthday.getMonthValue();
    dayOfBirthday = birthday.getDayOfMonth();
  }
}
