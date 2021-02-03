package com.safetynet.alerts_api.service.childrenAdults;

import com.safetynet.alerts_api.model.Person;
import java.util.List;
import java.util.Map;

public interface ChildrenAdultsService {

  /**
   * full a list of children and a list of adults from a given Person List.
   * 
   * @param personList   A Person List
   * @param childrenList A Person List (probably empty) to full of children
   * @param adultList    A Person List (probably empty) to full of adults
   * @return The three lists included in a Map object
   */
  public Map<String, List<Person>> fullChildrenListAndAdultListFromPersonList(List<Person> personList,
      List<Person> childrenList,
      List<Person> adultList);
}
