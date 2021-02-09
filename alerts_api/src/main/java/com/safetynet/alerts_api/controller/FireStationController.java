package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.exceptions.NonexistentException;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

  private static final Logger logger = LogManager.getLogger(FireStationController.class);

  @Autowired
  private FireStationService fireStationService;


  /**
   * Delete a fire station from a given address
   * 
   * @param id The id of the fire station to delete
   */
  @Transactional
  @DeleteMapping("/firestation")
  public void deleteFireStation(@RequestParam String address, @RequestParam Integer station) {
    boolean existingFireStation = false;
    boolean fireStationAddressAndStationExist = false;
    try {
      logger.info(
          "Delete request of the endpoint 'firestation' with the firestation number : {"
              + station.toString() + "} and the address : " + address);
      fireStationAddressAndStationExist = fireStationService.fireStationAddressAndStationNumberExist(address, station);
      if (!existingFireStation) {
        fireStationService.deleteFireStation(address, station);
      }
    } catch (Exception exception) {
      logger.error("Error in the FireStationController in the method deleteFireStation :"
          + exception.getMessage());
    }
    if (!fireStationAddressAndStationExist) {
      logger.error("The link with the firestation number " + station.toString() +
          " and the address " + address + " doesn't exist.");
      throw new NonexistentException("The link with the firestation number " + station.toString() +
          " and the address " + address + " doesn't exist.");
    }
    logger.info(
        "response following the Delete on the endpoint 'firestation' with the given firestation number : {"
            + station.toString() + "} and the address " + address);
  }


  /**
   * Update an existing fire station
   * 
   * @param address     The address of the firestation to update
   * @param fireStation The updated fireStation
   * @return The updated firestation object
   */
  @PutMapping("/firestation")
  public FireStation updateFireStation(@RequestParam String address, @RequestParam Integer station,
      @RequestBody FireStation fireStation) {
    FireStation fireStationToUpdate = null;
    boolean fireStationAddressAndStationExist = false;
    try {
      logger.info(
          "Put request of the endpoint 'firestation' with the given address : {" + address
              + "} and the given firestation number : "
              + station.toString());
      fireStationAddressAndStationExist = fireStationService.fireStationAddressAndStationNumberExist(address, station);
      if (fireStationAddressAndStationExist) {
        fireStationToUpdate = fireStationService.getFireStation(address, station);
        if (fireStationToUpdate != null) {
          Integer newStationNumber = fireStation.getStation();
          if (newStationNumber != null) {
            fireStationToUpdate.setStation(newStationNumber);
          }
          fireStationService.saveFireStation(fireStationToUpdate);
          logger.info(
              "response following the Put on the endpoint 'firestation' with the given address : {"
                  + address + "} and the given firestation number : " + station.toString());
        }
      }
    } catch (Exception exception) {
      logger.error("Error in the FireStationController in the method updateFireStation :"
          + exception.getMessage());
    }
    if (!fireStationAddressAndStationExist) {
      logger.error("The link between the given address {" + address + "} and the given firestation number {"
          + station.toString() + "} doesn't exist.");
      throw new NonexistentException(
          "The link between the given address {" + address + "} and the given firestation number {" + station.toString()
              + "} doesn't exist.");
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
