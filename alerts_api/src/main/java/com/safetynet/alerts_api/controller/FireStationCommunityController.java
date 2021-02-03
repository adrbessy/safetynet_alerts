package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.exceptions.NonexistentException;
import com.safetynet.alerts_api.model.FireDTOByAddress;
import com.safetynet.alerts_api.model.FireStationCommunity;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import com.safetynet.alerts_api.service.fireStationCommunity.FireStationCommunityService;
import com.safetynet.alerts_api.service.flood.FloodService;
import com.safetynet.alerts_api.service.phone.PhoneService;
import java.util.ArrayList;
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
  private FireStationCommunityService fireStationCommunityService;

  @Autowired
  private FloodService floodService;

  @Autowired
  private PhoneService phoneService;

  @Autowired
  private FireStationService fireStationService;

  /**
   * Get a person list covered by a given fire station with the number of
   * occurrences of children and adults.
   * 
   * @param stationNumber A fire station number
   * @return A List of persons covered by a FireStation and the number of children
   *         and adults
   */
  @GetMapping("/firestation")
  public FireStationCommunity getFireStationCommunity(@RequestParam Integer stationNumber) {
    boolean existingFireStationNumber = false;
    FireStationCommunity personNumberInfo = null;
    try {
      logger.info(
          "Get request of the endpoint 'fireStation' with the stationNumber : {" + stationNumber.toString() + "}");
      existingFireStationNumber = fireStationService.fireStationNumberExist(stationNumber);
      if (existingFireStationNumber) {
        personNumberInfo = fireStationCommunityService
            .getPersonNumberInfoListFromStationNumber(stationNumber);
        logger.info(
            "response following the Get on the endpoint 'firestation' with the given stationNumber : {"
                + stationNumber.toString() + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the FireStationCommunityController in the method getFireStationCommunity :"
          + exception.getMessage());
    }
    if (!existingFireStationNumber) {
      throw new NonexistentException(
          "The station number " + stationNumber.toString() + " doesn't exist.");
    }
    return personNumberInfo;
  }


  /**
   * Get the list of all phone numbers of persons covered by a given fire station.
   * 
   * @param firestation A fire station number
   * @return A List of phone number
   */
  @GetMapping("/phoneAlert")
  public List<String> getPhoneNumberCoveredByThisStation(@RequestParam Integer firestation) {
    boolean existingFireStationNumber = false;
    List<String> phoneList = null;
    try {
      logger.info(
          "Get request of the endpoint 'phoneAlert' with the firestationNumber : {" + firestation.toString() + "}");
      existingFireStationNumber = fireStationService.fireStationNumberExist(firestation);
      if (existingFireStationNumber) {
        phoneList = phoneService.getPhoneNumberList(firestation);
        logger.info("response following the Get on the endpoint 'phoneAlert' with the given firestation number : {"
            + firestation.toString() + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the FireStationCommunityController in the method getFireStationCommunity :"
          + exception.getMessage());
    }
    if (!existingFireStationNumber) {
      throw new NonexistentException(
          "The station number " + firestation.toString() + " doesn't exist.");
    }
    return phoneList;
  }


  /**
   * Get a person list sorted by address and sorted by the number of the fire
   * station deserving it.
   * 
   * @param stations A List of numbers of fire station
   * @return A List of PersonInfoByAddress object
   */
  @GetMapping("/flood")
  public List<FireDTOByAddress> getHomesCoveredByTheseStation(@RequestParam List<Integer> stations) {
    List<Integer> fireStationNumberNotFound = new ArrayList<>();
    List<FireDTOByAddress> personInfoByaddressList = null;
    try {
      logger.info(
          "Get request of the endpoint 'phoneAlert' with the firestationNumber : {" + stations.toString() + "}");
      fireStationNumberNotFound = fireStationService.fireStationNumberListExist(stations);
      if (fireStationNumberNotFound.isEmpty()) {
        personInfoByaddressList = floodService.getPersonInfoByAddressList(stations);
        logger.info(
            "response following the Get on the endpoint 'flood' with the given stationNumber List : {"
                + stations.toString() + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the FireStationCommunityController in the method getHomesCoveredByTheseStation :"
          + exception.getMessage());
    }
    if (!fireStationNumberNotFound.isEmpty()) {
      throw new NonexistentException(
          "The list of station number that doesn't exist : " + fireStationNumberNotFound.toString());
    }
    return personInfoByaddressList;
  }

}
