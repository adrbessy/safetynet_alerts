package com.safetynet.alerts_api.service.flood;

import com.safetynet.alerts_api.model.FireDTOByAddress;
import java.util.List;

public interface FloodService {

  /**
   * Get the List of person by address from a given fire station number List.
   * 
   * @param stationNumberList A List of fire station numbers
   * @return A List of PersonInfoByAddress includind a list of persons sorted by
   *         address
   */
  public List<FireDTOByAddress> getPersonInfoByAddressList(List<Integer> stationNumberList);

}
