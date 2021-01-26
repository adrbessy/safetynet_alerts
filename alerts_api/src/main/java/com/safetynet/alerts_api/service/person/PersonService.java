package com.safetynet.alerts_api.service.person;

import com.safetynet.alerts_api.model.Person;

public interface PersonService {


  /**
   * Delete a person.
   * 
   * @param a first name and a last name
   */
  public void deletePerson(String firstName, String lastName);


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
}
