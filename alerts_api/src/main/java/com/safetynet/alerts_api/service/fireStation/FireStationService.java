package com.safetynet.alerts_api.service.fireStation;

import com.safetynet.alerts_api.model.FireStation;
import java.util.List;

public interface FireStationService {

  /**
   * Read - Get the List of the fire station number from a list of fire station.
   * 
   * @param an address
   * @return - A List of Home
   */
  public List<Integer> getStationNumberListFromFireStationList(List<FireStation> fireStationList);

  /**
   * Save the fire station list
   * 
   * @param FireStationList to save
   * @return true if everything goes right, otherwise returns false
   * 
   */
  public boolean saveAllFireStations(List<FireStation> fireStationsList);
}
