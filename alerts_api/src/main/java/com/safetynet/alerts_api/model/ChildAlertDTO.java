package com.safetynet.alerts_api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildAlertDTO {

  private String lastName;

  private String firstName;

  private int age;

  public ChildAlertDTO(String lastName, String firstName, int age) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.age = age;
  }

}
