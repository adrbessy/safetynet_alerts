package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

@Data
public class Home {
  private List<ChildAlertDTO> childList;
  private List<ChildAlertDTO> adultList;

  public Home(List<ChildAlertDTO> childList, List<ChildAlertDTO> adultList) {
    this.childList = childList;
    this.adultList = adultList;
  }

}
