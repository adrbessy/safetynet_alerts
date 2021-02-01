package com.safetynet.alerts_api.service.fireStationCommunity;

import com.safetynet.alerts_api.model.FireStationCommunity;
import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface FireStationCommunityService {

  /**
   * Get the List of PersonNumberInfo covered by a given fire station number.
   * 
   * @param a fire station number
   * @return - A List of PersonNumberInfo
   */
  public FireStationCommunity getPersonNumberInfoListFromStationNumber(Integer stationNumber);


  /**
   * Get the List of Person covered by a given fire station number.
   * 
   * @param a fire station number
   * @return - A List of Person
   */
  public List<Person> getPersonListFromStationNumber(Integer stationNumber);

}
