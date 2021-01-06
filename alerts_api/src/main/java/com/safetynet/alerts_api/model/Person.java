package com.safetynet.alerts_api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@JsonFilter("dynamicFilter")
@Table(name = "persons")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  private String address;

  private String city;

  private String zip;

  private String phone;

  private String email;

  private int age;

  @ElementCollection
  private List<String> medications;

  @ElementCollection
  private List<String> allergies;

}