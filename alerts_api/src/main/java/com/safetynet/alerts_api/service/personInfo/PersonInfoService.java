package com.safetynet.alerts_api.service.personInfo;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import java.time.LocalDate;
import java.util.List;

public interface PersonInfoService {

  /**
   * Get the a list of persons with a given first name and a given last name
   * 
   * @param firstName The first name of the person
   * @param lastName  The last name of the person
   * @return A List of Person
   */
  public List<Person> getPersonListByFirstNameAndLastName(String firstName, String lastName);


  /**
   * Get the Person List from a given last name.
   * 
   * @param lastName The last name of the person
   * @return A List of Person
   */
  public List<Person> getPersonListByLastName(String lastName);


  /**
   * Get the a list of persons with a given first name and a given last name, then
   * only with the given last name
   * 
   * @param firstName The first name of the person
   * @param lastName  The last name of the person
   * @return A List of Person
   */
  public List<PersonInfoDTO> getPersonListByFirstNameAndLastNameThenOnlyLastName(String firstName, String lastName);

  /**
   * Set the age, the medications and the allergies to persons from a PersonList.
   * 
   * @param personList The given person list
   */
  public void setAgeAndMedicationsAndAllergiesFromPersonList(List<Person> personList, LocalDate aDate);

}
