package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.model.FireDTOByAddress;
import com.safetynet.alerts_api.model.FireStationCommunity;
import com.safetynet.alerts_api.service.person.PersonService;
import com.safetynet.alerts_api.service.phone.PhoneService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationCommunityController {

  private static final Logger logger = LogManager.getLogger(FireStationCommunityController.class);

  @Autowired
  private PersonService personService;

  @Autowired
  private PhoneService phoneService;

  /**
   * Read - Get a person list covered by a given fire station with the number of
   * occurrences of children and adults.
   * 
   * @param a fire station number
   * @return A List of persons covered by a FireStation and the number of children
   *         and adults
   */
  @GetMapping("/firestation")
  public FireStationCommunity getPersonListCoveredByThisStation(@RequestParam Integer stationNumber) {
    logger.info(
        "Get request of the endpoint 'fireStation' with the stationNumber : {" + stationNumber.toString() + "}");
    FireStationCommunity personNumberInfo = personService
        .getPersonNumberInfoListFromStationNumber(stationNumber);
    logger.info(
        "response following the Get on the endpoint 'firestation' with the given stationNumber : {"
            + stationNumber.toString() + "}");
    return personNumberInfo;
  }


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


  /**
   * Read - Get a person list grouped by address and grouped by the number of the
   * fire station deserving it.
   * 
   * @param a List of number of fire station
   * @return - A List of PersonInfoByAddress
   */
  @GetMapping("/flood")
  public List<FireDTOByAddress> getHomesCoveredByTheseStation(@RequestParam List<Integer> stations) {
    logger.info(
        "Get request of the endpoint 'phoneAlert' with the firestationNumber : {" + stations.toString() + "}");
    List<FireDTOByAddress> personInfoByaddressList = personService.getPersonInfoByAddressList(stations);
    logger.info(
        "response following the Get on the endpoint 'flood' with the given stationNumber List : {"
            + stations.toString() + "}");
    return personInfoByaddressList;
  }



}
