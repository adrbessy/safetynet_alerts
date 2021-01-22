package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

@Data
public class FireDTOByAddress {

  private String address;
  private List<FireDTO> personList;

  public FireDTOByAddress(String address, List<FireDTO> personList) {
    this.address = address;
    this.personList = personList;
  }

}
