package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

@Data
public class Home {
  private List<Person> childList;
  private List<Person> adultList;

  public Home(List<Person> childList, List<Person> adultList) {
    this.childList = childList;
    this.adultList = adultList;
  }

}
