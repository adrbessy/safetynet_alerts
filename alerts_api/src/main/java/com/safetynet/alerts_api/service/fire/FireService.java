package com.safetynet.alerts_api.service.fire;

import com.safetynet.alerts_api.model.Fire;

public interface FireService {

  /**
   * Get a Person List with the number of fire station from an address.
   * 
   * @param address An address
   * @return A Fire object including a list of persons with the associated
   *         firestation number
   */
  public Fire getPersonListWithStationNumber(String address);

}
