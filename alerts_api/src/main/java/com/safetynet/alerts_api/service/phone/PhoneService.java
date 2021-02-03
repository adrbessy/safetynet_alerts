package com.safetynet.alerts_api.service.phone;

import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface PhoneService {

  /**
   * Get the List of phone number from a List of Person.
   * 
   * @param personList
   * @return A List of phone number
   */
  List<String> getPhoneListFromPersonList(List<Person> personList);


  /**
   * Get the List of phone numbers of persons covered by a given fire station
   * number.
   * 
   * @param firestation A fire station number
   * @return A List of phone numbers
   */
  public List<String> getPhoneNumberList(Integer firestation);

}
