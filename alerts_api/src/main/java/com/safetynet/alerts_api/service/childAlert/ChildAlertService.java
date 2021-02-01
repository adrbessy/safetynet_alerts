package com.safetynet.alerts_api.service.childAlert;

import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface ChildAlertService {

  /**
   * Get the List of children and adults living to a given address.
   * 
   * @param an address
   * @return - A List of Home
   */
  public Home getChildrenListAndAdultListFromAddress(String address);

  /**
   * full the List of children and adults from a given Person List.
   * 
   * @param a Person List, a Person List, a Person List
   */
  public void fullChildrenListAndAdultListFromPersonList(List<Person> personList, List<Person> childrenList,
      List<Person> adultList);


}
