package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.service.phone.PhoneService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityPhoneController {

  private static final Logger logger = LogManager.getLogger(CommunityPhoneController.class);

  @Autowired
  private PhoneService phoneService;

  /**
   * Read - Get the list of all phone numbers of persons covered by a given fire
   * station.
   * 
   * @param a fire station number
   * @return - A List of phone number
   */
  @GetMapping("/phoneAlert")
  public List<String> getPhoneNumberCoveredByThisStation(@RequestParam Integer firestation) {
    logger.info(
        "Get request of the endpoint 'phoneAlert' with the firestationNumber : {" + firestation.toString() + "}");
    List<String> phoneList = phoneService.getPhoneNumberList(firestation);
    logger.info("response following the Get on the endpoint 'phoneAlert' with the given firestation number : {"
        + firestation.toString() + "}");
    return phoneList;
  }

}
