package com.safetynet.alerts_api.service.person;

import com.safetynet.alerts_api.model.Fire;
import com.safetynet.alerts_api.model.FireDTOByAddress;
import com.safetynet.alerts_api.model.FireStationCommunity;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import java.time.LocalDate;
import java.util.List;

public interface PersonService {


  /**
   * Delete a person.
   * 
   * @param a first name and a last name
   */
  public void deletePerson(String firstName, String lastName);


  /**
   * Get the List of Person covered by a given fire station number.
   * 
   * @param a fire station number
   * @return - A List of Person
   */
  public List<Person> getPersonListFromStationNumber(Integer stationNumber);

  /**
   * Get the List of PersonNumberInfo covered by a given fire station number.
   * 
   * @param a fire station number
   * @return - A List of PersonNumberInfo
   */
  public FireStationCommunity getPersonNumberInfoListFromStationNumber(Integer stationNumber);

  /**
   * full the List of children and adults from a given Person List.
   * 
   * @param a Person List, a Person List, a Person List
   */
  public void fullChildrenListAndAdultListFromPersonList(List<Person> personList, List<Person> childrenList,
      List<Person> adultList);

  /**
   * add a person to a List (childrenList or adultList) from a given Person and a
   * medicalRecordList according to the person age.
   * 
   * @param a Person, medicalRecordList, PersonList, PersonList, LocalDate
   */
  public void addPersonToListFromFireStationList(Person personIterator, List<MedicalRecord> medicalRecordList,
      List<Person> childrenList,
      List<Person> adultList, LocalDate currentDate);

  /**
   * Set the age, the medications and the allergies to persons from a PersonList.
   * 
   * @param a PersonList
   */
  public void setAgeAndMedicationsAndAllergiesFromPersonList(List<Person> personList);

  /**
   * Get the List of children and adults living to a given address.
   * 
   * @param an address
   * @return - A List of Home
   */
  public Home getChildrenListAndAdultListFromAddress(String address);


  /**
   * Get a Person
   * 
   * @param Person to get
   * @return the person
   * 
   */
  public Person getPerson(final Long id);


  /**
   * Get the List of person by address from a given fire station number List.
   * 
   * @param a List of fire station number
   * @return - A List of PersonInfoByAddress
   */
  public List<FireDTOByAddress> getPersonInfoByAddressList(List<Integer> stationsList);

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
   * Get the Person List from a given address.
   * 
   * @param an address
   * @return - A List of Person
   */
  public List<Person> getPersonListByAddress(String address);

  /**
   * Get the Person List with the number of fire station.
   * 
   * @param an address
   * @return - A List of PersonInfo
   */
  public Fire getPersonListWithStationNumber(String address);

  /**
   * Save the person list
   * 
   * @param personList Person List to save
   * @return true if everything goes right, otherwise returns false
   * 
   */
  public boolean saveAllPersons(List<Person> personList);

  /**
   * Save a Person
   * 
   * @param Person to save
   * @return the saved person
   * 
   */
  public Person savePerson(Person person);
}
