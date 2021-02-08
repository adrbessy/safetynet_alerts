package com.safetynet.alerts_api.service.address;

import com.safetynet.alerts_api.model.FireStation;
import java.util.List;

public interface AddressService {

  /**
   * Get the List of address of persons covered by a fire station number list.
   * 
   * @param stationNumberList A list of fire station numbers
   * @return A List of address without duplicates
   */
  public List<String> getAddressListFromStationNumberList(List<Integer> stationNumberList);

  /**
   * Get the List of address of persons covered by a fire station list.
   * 
   * @param fireStationList A List of FireStation
   * @return A List of address
   */
  public List<String> getAddressListFromFireStationList(List<FireStation> fireStationList);

  /**
   * Add the address of persons to a list from FireStation list.
   * 
   * @param fireStationList A List of FireStation
   * @param addressList     A (probably empty) List of address
   */
  public void addAddressToListFromFireStationList(List<FireStation> fireStationList, List<String> addressList);

}
