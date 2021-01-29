package com.safetynet.alerts_api.service.fireStation;

import com.safetynet.alerts_api.model.FireStation;
import java.util.List;

public interface FireStationService {


  /**
   * Delete a FireStation
   * 
   * @param FireStation to delete
   * @return boolean
   */
  public void deleteFireStation(String address);


  /**
   * Get a FireStation
   * 
   * @param FireStation to get
   * @return the fireStation
   * 
   */
  public FireStation getFireStation(final String address);


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


  /**
   * Check if the fire station number exists in the database.
   * 
   * @param a Integer firestation number
   * @return - A boolean
   */
  public boolean fireStationNumberExist(Integer stationNumber);

  /**
   * Check if all the fire station number exists in the database.
   * 
   * @param a List<Integer> of firestation number
   * @return - A boolean
   */
  public List<Integer> fireStationNumberListExist(List<Integer> stationNumber);

  /**
   * Check if the fire station address exists in the database.
   * 
   * @param a List<Integer> of firestation number
   * @return - A boolean
   */
  public boolean fireStationAddressExist(String address);


  public void deleteFireStation(Long id);

  /*
   * public void deleteFireStationByAddress(String address);
   */

}
