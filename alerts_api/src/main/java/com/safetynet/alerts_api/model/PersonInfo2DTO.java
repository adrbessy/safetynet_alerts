package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

@Data
public class PersonInfo2DTO {

  private String lastName;

  private int age;

  private String address;

  private String city;

  private String zip;

  private String email;

  private List<String> medications;

  private List<String> allergies;

  public PersonInfo2DTO(String lastName, int age, String address, String city, String zip, String email,
      List<String> medications, List<String> allergies) {
    this.lastName = lastName;
    this.age = age;
    this.address = address;
    this.city = city;
    this.zip = zip;
    this.email = email;
    this.medications = medications;
    this.allergies = allergies;
  }

}
