package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

@Data
public class FireDTO {

  private String lastName;

  private int age;

  private String phone;

  private List<String> medications;

  private List<String> allergies;

  public FireDTO(String lastName, int age, String phone, List<String> medications, List<String> allergies) {
    this.lastName = lastName;
    this.age = age;
    this.phone = phone;
    this.medications = medications;
    this.allergies = allergies;
  }

}
