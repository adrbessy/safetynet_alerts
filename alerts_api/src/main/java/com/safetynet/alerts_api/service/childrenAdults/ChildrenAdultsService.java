package com.safetynet.alerts_api.service.childrenAdults;

import com.safetynet.alerts_api.model.Person;
import java.util.List;
import java.util.Map;

public interface ChildrenAdultsService {

  /**
   * full the List of children and adults from a given Person List.
   * 
   * @param a Person List, a Person List, a Person List
   */
  public Map<String, List<Person>> fullChildrenListAndAdultListFromPersonList(List<Person> personList,
      List<Person> childrenList,
      List<Person> adultList);

}
