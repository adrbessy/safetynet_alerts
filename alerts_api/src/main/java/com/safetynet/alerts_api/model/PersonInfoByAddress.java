package com.safetynet.alerts_api.model;

import java.util.List;
import lombok.Data;

@Data
public class PersonInfoByAddress {

  private String address;
  private List<PersonInfoDTO> personList;

  public PersonInfoByAddress(String address, List<PersonInfoDTO> personList) {
    this.address = address;
    this.personList = personList;
  }

}
