package com.safetynet.alerts_api.model;

import java.util.List;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "medicalrecords")
public class Medicalrecord {

  private String firstName;

  private String lastName;

  private String birthdate;

  private List<String> medications;

  private List<String> allergies;

}