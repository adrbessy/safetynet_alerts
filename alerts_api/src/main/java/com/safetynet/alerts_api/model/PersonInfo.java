package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

@Data
public class PersonInfo {

  private List<Person> personList;
  private List<Integer> firestationNumber;

  public PersonInfo(List<Person> personList, List<Integer> firestationNumber) {
    this.personList = personList;
    this.firestationNumber = firestationNumber;
  }

}
