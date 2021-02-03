package com.safetynet.alerts_api.service.fireStation;

import com.safetynet.alerts_api.model.FireStation;
import java.util.List;

public interface FireStationService {


  /**
   * Delete a fireStation corresponding to a given address
   * 
   * @param address An address corresponding to the fireStation to delete
   * @return True if the firestation had been deleted, False if not
   */
  public void deleteFireStation(String address);


  /**
   * Delete a fireStation corresponding to a given id
   * 
   * @param id An id corresponding to the fireStation to delete
   * @return True if the firestation had been deleted, False if not
   */
  public void deleteFireStation(Long id);


  /**
   * Get a FireStation
   * 
   * @param address An address correspnding to a FireStation to get
   * @return the fireStation if it exists, null if not
   */
  public FireStation getFireStation(final String address);


  /**
   * Get a list of the fire station numbers from a list of fire stations.
   * 
   * @param fireStationList A list of fire stations
   * @return A list of fire station numbers
   */
  public List<Integer> getStationNumberListFromFireStationList(List<FireStation> fireStationList);


  /**
   * Save the given fire station list in the data base
   * 
   * @param fireStationsList A list of fire stations to save
   * @return true if everything goes right, otherwise returns false
   */
  public boolean saveAllFireStations(List<FireStation> fireStationsList);


  /**
   * Save the given fire station
   * 
   * @param fireStation A fire station to save
   * @return The saved firestation
   */
  public FireStation saveFireStation(FireStation fireStation);


  /**
   * Check if the given fire station number exists in the database.
   * 
   * @param stationNumber A firestation number
   * @return true if it exists, otherwise returns false
   */
  public boolean fireStationNumberExist(Integer stationNumber);


  /**
   * Check if the fire station number in the given list exists in the database.
   * 
   * @param stationNumberList A list of firestation number
   * @return the list of firestation numbers that has not been found
   */
  public List<Integer> fireStationNumberListExist(List<Integer> stationNumber);


  /**
   * Check if the given fire station address exists in the database.
   * 
   * @param address An address
   * @return true if it exists, otherwise returns false
   */
  public boolean fireStationAddressExist(String address);

}
