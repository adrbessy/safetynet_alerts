package com.safetynet.alerts_api.service.fireStation;

import com.safetynet.alerts_api.model.FireStation;
import java.util.List;
import java.util.Optional;

public interface FireStationService {


  /**
   * Delete a FireStation
   * 
   * @param FireStation to delete
   * 
   */
  public void deleteFireStation(final Long id);


  /**
   * Get a FireStation
   * 
   * @param FireStation to get
   * @return the fireStation
   * 
   */
  public Optional<FireStation> getFireStation(final Long id);


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

  /**
   * Save the fire station
   * 
   * @param FireStation to save
   * @return the saved firestation
   * 
   */
  public FireStation saveFireStation(FireStation fireStation);
}
