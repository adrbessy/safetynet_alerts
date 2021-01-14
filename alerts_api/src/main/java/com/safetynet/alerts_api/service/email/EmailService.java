package com.safetynet.alerts_api.service.email;

import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface EmailService {

  /**
   * Read - Get the List of email of persons living in a given city.
   * 
   * @param an city
   * @return - A List of email
   */
  public List<String> getPersonEmailFromCity(String city);

  /**
   * Read - Get the List of email of persons from a Person List.
   * 
   * @param a Person List
   * @return - A List of email
   */
  public List<String> getEmailListFromPersonList(List<Person> personList);

}
