package com.safetynet.alerts_api.service.childAlert;

import com.safetynet.alerts_api.model.Home;

public interface ChildAlertService {

  /**
   * Get the List of children and adults living to a given address.
   * 
   * @param an address
   * @return - A List of Home
   */
  public Home getChildrenListAndAdultListFromAddress(String address);

}
