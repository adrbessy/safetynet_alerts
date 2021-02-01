package com.safetynet.alerts_api.service.flood;

import com.safetynet.alerts_api.model.FireDTOByAddress;
import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface FloodService {


  /**
   * Get the List of person by address from a given fire station number List.
   * 
   * @param a List of fire station number
   * @return - A List of PersonInfoByAddress
   */
  public List<FireDTOByAddress> getPersonInfoByAddressList(List<Integer> stationsList);

  /**
   * Get the List of person by address from a given address.
   * 
   * @param an address
   * @return - A List of Person
   */
  List<Person> getPersonListByAddress(String address);

  /**
   * Set the age, the medications and the allergies to persons from a PersonList.
   * 
   * @param a PersonList
   */
  public void setAgeAndMedicationsAndAllergiesFromPersonList(List<Person> personList);

}
