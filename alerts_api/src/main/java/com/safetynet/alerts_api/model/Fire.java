package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

@Data
public class Fire {

  private List<FireDTO> personList;
  private List<Integer> firestationNumberList;

  public Fire(List<FireDTO> personList, List<Integer> firestationNumberList) {
    this.personList = personList;
    this.firestationNumberList = firestationNumberList;
  }

}
