package com.safetynet.alerts_api.service.fire;

import com.safetynet.alerts_api.model.Fire;
import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface FireService {

  /**
   * Get the Person List with the number of fire station.
   * 
   * @param an address
   * @return - A List of PersonInfo
   */
  public Fire getPersonListWithStationNumber(String address);


  /**
   * Get the Person List from a given address.
   * 
   * @param an address
   * @return - A List of Person
   */
  public List<Person> getPersonListByAddress(String address);

  /**
   * Set the age, the medications and the allergies to persons from a PersonList.
   * 
   * @param a PersonList
   */
  public void setAgeAndMedicationsAndAllergiesFromPersonList(List<Person> personList);
}
