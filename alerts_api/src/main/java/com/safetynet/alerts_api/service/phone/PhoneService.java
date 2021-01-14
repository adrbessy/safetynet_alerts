package com.safetynet.alerts_api.service.phone;

import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface PhoneService {

  /**
   * Read - Get the List of phone number from a List of Person.
   * 
   * @param a List of Person
   * @return - A List of phone number
   */
  public List<String> getPhoneListFromPersonList(List<Person> personList);

  /**
   * Read - Get the List of phone number of persons covered by a given fire
   * station number.
   * 
   * @param a fire station number
   * @return - A List of phone number
   */
  public List<String> getPhoneNumberList(Integer firestation);
}
