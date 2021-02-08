package com.safetynet.alerts_api.service.home;

import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface HomeService {

  /**
   * Get a list of persons by address from a given address.
   * 
   * @param address An address
   * @return A List of Person
   */
  List<Person> getPersonListByAddress(String address);

}
