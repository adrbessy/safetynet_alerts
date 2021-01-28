package com.safetynet.alerts_api.service.person;

import com.safetynet.alerts_api.model.Person;

public interface PersonService {


  /**
   * Delete a person.
   * 
   * @param a first name and a last name
   * @return boolean
   */
  public boolean deletePerson(String firstName, String lastName);


  /**
   * Get a Person
   * 
   * @param Person to get
   * @return the person
   * 
   */
  public Person getPerson(final Long id);


  /**
   * Save a Person
   * 
   * @param Person to save
   * @return the saved person
   * 
   */
  public Person savePerson(Person person);


  /**
   * Check if the city exists in the database.
   * 
   * @param a String city
   * @return - A boolean
   */
  public boolean cityExist(String city);

  /**
   * Check if the address exists in the database.
   * 
   * @param a String address
   * @return - A boolean
   */
  public boolean personAddressExist(String address);
}
