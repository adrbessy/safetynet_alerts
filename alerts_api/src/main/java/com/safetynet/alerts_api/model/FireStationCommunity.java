package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

/*
 * Used to display the list of persons covered by a particular fire station with the number of children and the number of adults
 */
@Data
public class FireStationCommunity {


  private List<FireStationCommunityDTO> personList;
  private int numberOfChildren;
  private int numberOfAdults;

  public FireStationCommunity(List<FireStationCommunityDTO> personList, int numberOfChildren, int numberOfAdults) {
    this.personList = personList;
    this.numberOfChildren = numberOfChildren;
    this.numberOfAdults = numberOfAdults;
  }

}
