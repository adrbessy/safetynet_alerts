package com.safetynet.alerts_api.service.childAlert;

import com.safetynet.alerts_api.model.Home;

public interface ChildAlertService {

  /**
   * Get the List of children and adults living at a given address.
   * 
   * @param address An address
   * @return A Home object including a list of children and a list of adults
   */
  public Home getChildrenListAndAdultListFromAddress(String address);

}
