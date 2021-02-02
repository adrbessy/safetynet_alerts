package com.safetynet.alerts_api.service.flood;

import com.safetynet.alerts_api.model.FireDTOByAddress;
import java.util.List;

public interface FloodService {


  /**
   * Get the List of person by address from a given fire station number List.
   * 
   * @param a List of fire station number
   * @return - A List of PersonInfoByAddress
   */
  public List<FireDTOByAddress> getPersonInfoByAddressList(List<Integer> stationsList);

}
