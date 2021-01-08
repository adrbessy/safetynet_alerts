package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

/*
 * Used to display the list of persons covered by a particular fire station with the number of children and the number of adults
 */
@Data
public class FireStationInfo {


  private List<Person> personList;
  private int numberOfChildren;
  private int numberOfAdults;

  public FireStationInfo(List<Person> personList, int numberOfChildren, int numberOfAdults) {
    this.personList = personList;
    this.numberOfChildren = numberOfChildren;
    this.numberOfAdults = numberOfAdults;
  }

}
