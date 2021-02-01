package com.safetynet.alerts_api.service.personInfo;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import java.util.List;

public interface PersonInfoService {
  /**
   * Get the Person List from a given first name and a given last name.
   * 
   * @param a first name and a last name
   * @return - A List of Person
   */
  public List<Person> getPersonListByFirstNameAndLastName(String firstName, String lastName);

  /**
   * Get the Person List from a given last name.
   * 
   * @param a last name
   * @return - A List of Person
   */
  public List<Person> getPersonListByLastName(String lastName);

  /**
   * Get the Person List from a given first name and a given last name, then from
   * only the last name
   * 
   * @param a first name and a last name
   * @return - A List of Person
   */
  public List<PersonInfoDTO> getPersonListByFirstNameAndLastNameThenOnlyLastName(String firstName, String lastName);

  /**
   * Set the age, the medications and the allergies to persons from a PersonList.
   * 
   * @param a PersonList
   */
  public void setAgeAndMedicationsAndAllergiesFromPersonList(List<Person> personList);

}
