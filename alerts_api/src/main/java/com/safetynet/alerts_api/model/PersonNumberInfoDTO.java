package com.safetynet.alerts_api.model;

import lombok.Data;

@Data
public class PersonNumberInfoDTO {

  private String firstName;

  private String lastName;

  private String address;

  private String city;

  private String zip;

  private String phone;

  public PersonNumberInfoDTO(String firstName, String lastName, String address, String city, String zip, String phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.city = city;
    this.zip = zip;
    this.phone = phone;
  }

}
