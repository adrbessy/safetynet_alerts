package com.safetynet.alerts_api.service.fireStationCommunity;

import com.safetynet.alerts_api.model.FireStationCommunity;
import com.safetynet.alerts_api.model.Person;
import java.util.List;

public interface FireStationCommunityService {

  /**
   * Get a list of persons covered by a given fire station number with the number
   * of children and the number of adults
   * 
   * @param stationNumber A fire station number
   * @return A FireStationCommunity object including a list of persons and the
   *         number of children and the number of adults
   */
  public FireStationCommunity getPersonNumberInfoListFromStationNumber(Integer stationNumber);


  /**
   * Get a list of persons covered by a given fire station number.
   * 
   * @param stationNumber A fire station number
   * @return A List of persons
   */
  public List<Person> getPersonListFromStationNumber(Integer stationNumber);

}
