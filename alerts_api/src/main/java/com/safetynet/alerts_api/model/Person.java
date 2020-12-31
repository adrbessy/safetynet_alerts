package com.safetynet.alerts_api.model;

import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "persons")
public class Person {

  private String firstName;

  private String lastName;

  private String address;

  private String city;

  private String zip;

  private String phone;

  private String email;

}