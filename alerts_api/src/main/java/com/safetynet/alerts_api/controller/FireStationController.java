package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.exceptions.NonexistentException;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

  private static final Logger logger = LogManager.getLogger(FireStationController.class);

  @Autowired
  private FireStationService fireStationService;


  /**
   * Delete a fire station from a given address
   * 
   * @param address The address of the fire station to delete
   */
  @Transactional
  @DeleteMapping("/firestation/{address}")
  public void deleteFireStation(@PathVariable("address") final String address) {
    boolean existingFireStation = false;
    boolean fireStationAddressExist = false;
    try {
      logger.info(
          "Delete request of the endpoint 'firestation' with the firestation address : {"
              + address + "}");
      fireStationAddressExist = fireStationService.fireStationAddressExist(address);
      if (!existingFireStation) {
        fireStationService.deleteFireStation(address);
      }
    } catch (Exception exception) {
      logger.error("Error in the FireStationController in the method deleteFireStation :"
          + exception.getMessage());
    }
    if (!fireStationAddressExist) {
      logger.error("The firestation with the address " + address +
          " doesn't exist.");
      throw new NonexistentException("The firestation with the address " + address +
          " doesn't exist.");
    }
    logger.info(
        "response following the Delete on the endpoint 'firestation' with the given address : {"
            + address + "}");
  }


  /**
   * Update an existing fire station
   * 
   * @param address     The address of the firestation to update
   * @param fireStation The updated fireStation
   * @return The updated firestation object
   */
  @PutMapping("/firestation/{address}")
  public FireStation updateFireStation(@PathVariable("address") final String address,
      @RequestBody FireStation fireStation) {
    FireStation fireStationToUpdate = null;
    boolean existingFireStationAddress = false;
    try {
      logger.info(
          "Put request of the endpoint 'firestation' with the firestation address : {" + address + "}");
      existingFireStationAddress = fireStationService.fireStationAddressExist(address);
      if (existingFireStationAddress) {
        fireStationToUpdate = fireStationService.getFireStation(address);
        if (fireStationToUpdate != null) {
          Integer station = fireStation.getStation();
          if (station != null) {
            fireStationToUpdate.setStation(station);
          }
          fireStationService.saveFireStation(fireStationToUpdate);
          logger.info(
              "response following the Put on the endpoint 'firestation' with the given address : {"
                  + address + "}");
        }
      }
    } catch (Exception exception) {
      logger.error("Error in the FireStationController in the method updateFireStation :"
          + exception.getMessage());
    }
    if (!existingFireStationAddress) {
      logger.error("The station number address" + address + " doesn't exist.");
      throw new NonexistentException(
          "The station number address" + address + " doesn't exist.");
    }
    return fireStationToUpdate;
  }


  /**
   * Create a new fire station
   * 
   * @param fireStation The new firestation
   * @return The saved fire station object
   */
  @PostMapping("/firestation")
  public FireStation createFireStation(@RequestBody FireStation fireStation) {
    FireStation savedFireStation = null;
    try {
      logger.info(
          "Post request of the endpoint 'firestation' with the firestation : {" + fireStation.toString() + "}");
      savedFireStation = fireStationService.saveFireStation(fireStation);
      logger.info(
          "response following the Post on the endpoint 'firestation' with the given fireStation : {"
              + fireStation.toString() + "}");
    } catch (Exception exception) {
      logger.error("Error in the fireStationController in the method createFireStation :"
          + exception.getMessage());
    }
    return savedFireStation;
  }

}
