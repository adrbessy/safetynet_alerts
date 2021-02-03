package com.safetynet.alerts_api.service.email;

import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface EmailService {

  /**
   * Read - Get the list of email address of persons living in a given city.
   * 
   * @param city A city
   * @return A list of email address
   */
  public List<String> getPersonEmailFromCity(String city);


  /**
   * Get the list of email address of persons from a Person List.
   * 
   * @param personList A Person List
   * @return A list of email address
   */
  public List<String> getEmailListFromPersonList(List<Person> personList);

}
