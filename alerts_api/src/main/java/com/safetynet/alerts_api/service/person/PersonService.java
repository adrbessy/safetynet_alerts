package com.safetynet.alerts_api.service.person;

import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface PersonService {


  /**
   * Delete a person.
   * 
   * @param firstName
   * @param lastName
   * @return true if it had been deleted, otherwise returns false
   */
  public void deletePerson(String firstName, String lastName);


  /**
   * Get a Person from an id
   * 
   * @param id
   * @return The person
   */
  public Person getPerson(final Long id);


  /**
   * Save a Person
   * 
   * @param person A Person to save
   * @return the saved person
   */
  public Person savePerson(Person person);


  /**
   * Check if a given city exists in the person table.
   * 
   * @param city
   * @return true if it exists, otherwise returns false
   */
  public boolean cityExist(String city);


  /**
   * Check if the address exists in the person table.
   * 
   * @param address
   * @return true if it exists, otherwise returns false
   */
  public boolean personAddressExist(String address);


  /**
   * Check if the id exists in the person table.
   * 
   * @param id
   * @return true if it exists, otherwise returns false
   */
  public boolean personIdExist(Long id);


  /**
   * Check if the person exists in the person table.
   * 
   * @param firstName
   * @param lastName
   * @return true if it exists, otherwise returns false
   */
  public boolean personFirstNameLastNameExist(String firstName, String lastName);


  /**
   * Save the person list
   * 
   * @param personList A Person List to save
   * @return true if everything goes right, otherwise returns false
   */
  public boolean saveAllPersons(List<Person> personList);
}
