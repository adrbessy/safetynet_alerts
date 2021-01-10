package com.safetynet.alerts_api.service.address;

import com.safetynet.alerts_api.model.FireStation;
import java.util.List;

public interface AddressService {

  /**
   * Read - Get the List of address of persons covered by a fire station number
   * list.
   * 
   * @param a List of fire station number
   * @return - A List of address
   */
  public List<String> getAddressListFromStationNumberList(List<Integer> stationsList);

  /**
   * Read - Get the List of address of persons covered by a fire station list.
   * 
   * @param a List of FireStation
   * @return - A List of address
   */
  public List<String> getAddressListFromFireStationList(List<FireStation> fireStationList);

}
