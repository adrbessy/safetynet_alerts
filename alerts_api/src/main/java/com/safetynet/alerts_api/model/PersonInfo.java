package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

@Data
public class PersonInfo {

  private List<Person> personList;
  private List<Integer> firestationNumberList;

  public PersonInfo(List<Person> personList, List<Integer> firestationNumberList) {
    this.personList = personList;
    this.firestationNumberList = firestationNumberList;
  }

}
