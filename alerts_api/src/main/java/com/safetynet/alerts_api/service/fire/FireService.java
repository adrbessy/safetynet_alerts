package com.safetynet.alerts_api.service.fire;

import com.safetynet.alerts_api.model.Fire;

public interface FireService {

  /**
   * Get the Person List with the number of fire station.
   * 
   * @param an address
   * @return - A List of PersonInfo
   */
  public Fire getPersonListWithStationNumber(String address);

}
