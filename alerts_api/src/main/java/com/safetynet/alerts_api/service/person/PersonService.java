package com.safetynet.alerts_api.service.person;

import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfo;
import com.safetynet.alerts_api.model.PersonInfoByAddress;
import com.safetynet.alerts_api.model.PersonNumberInfo;
import java.util.List;

public interface PersonService {

  /**
   * Read - Get the List of PersonNumberInfo covered by a given fire station
   * number.
   * 
   * @param an fire station number
   * @return - A List of PersonNumberInfo
   */
  public List<PersonNumberInfo> getPersonNumberList(Integer stationNumber);

  /**
   * Read - full the List of children and adults from a given Person List.
   * 
   * @param a Person List, a Person List, a Person List
   */
  public void fullChildrenListAndAdultListFromPersonList(List<Person> personList, List<Person> childrenList,
      List<Person> adultList);

  /**
   * Read - Get the List of children and adults living to a given address.
   * 
   * @param an address
   * @return - A List of Home
   */
  public List<Home> getChildrenListAndAdultListFromAddress(String address);

  /**
   * Read - Get the List of person by address from a given fire station number
   * List.
   * 
   * @param a List of fire station number
   * @return - A List of PersonInfoByAddress
   */
  public List<PersonInfoByAddress> getPersonInfoByAddressList(List<Integer> stationsList);

  /**
   * Read - Get the Person List from a given first name and a given last name.
   * 
   * @param a first name and a last name
   * @return - A List of Person
   */
  public List<Person> getPersonListByFirstNameAndLastName(String firstName, String lastName);

  /**
   * Read - Get the Person List from a given last name.
   * 
   * @param a last name
   * @return - A List of Person
   */
  public List<Person> getPersonListByLastName(String lastName);

  /**
   * Read - Get the Person List from a given first name and a given last name,
   * then from only the last name
   * 
   * @param a first name and a last name
   * @return - A List of Person
   */
  public List<Person> getPersonListByFirstNameAndLastNameThenOnlyLastName(String firstName, String lastName);

  /**
   * Read - Get the Person List from a given address.
   * 
   * @param an address
   * @return - A List of Person
   */
  public List<Person> getPersonListByAddress(String address);

  /**
   * Read - Get the Person List with the number of fire station.
   * 
   * @param an address
   * @return - A List of PersonInfo
   */
  public List<PersonInfo> getPersonListWithStationNumber(String address);

  /**
   * Save the person list
   * 
   * @param personList Person List to save
   * @return true if everything goes right, otherwise returns false
   * 
   */
  public boolean saveAllPersons(List<Person> personList);
}
