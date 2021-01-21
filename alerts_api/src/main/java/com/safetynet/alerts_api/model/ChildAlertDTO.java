package com.safetynet.alerts_api.model;

import lombok.Data;

@Data
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
