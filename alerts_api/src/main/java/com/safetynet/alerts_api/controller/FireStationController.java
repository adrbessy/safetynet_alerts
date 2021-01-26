package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
   * Delete - Delete a fire station
   * 
   * @param id - The id of the fire station to delete
   */
  @DeleteMapping("/firestation/{id}")
  public void deleteFireStation(@PathVariable("id") final Long id) {
    try {
      logger.info(
          "Delete request of the endpoint 'firestation' with the firestation Id : {" + id.toString() + "}");
      fireStationService.deleteFireStation(id);
      logger.info(
          "response following the Delete on the endpoint 'firestation' with the given id : {"
              + id.toString() + "}");
    } catch (Exception exception) {
      logger.error("Error in the fireStationController in the method deleteFireStation :"
          + exception.getMessage());
    }
  }


  /**
   * Update - Update an existing fire station
   * 
   * @param address - The address of the firestation to update
   * @return firestation - The fire station object updated
   */
  @PutMapping("/firestation/{address}")
  public FireStation updateFireStation(@PathVariable("address") final String address,
      @RequestBody FireStation fireStation) {
    logger.info(
        "Put request of the endpoint 'firestation' with the firestation address : {" + address + "}");
    FireStation fireStationToUpdate = fireStationService.getFireStation(address);
    logger.info(
        "response following the Put on the endpoint 'firestation' with the given address : {"
            + address + "}");
    if (fireStationToUpdate != null) {
      Integer station = fireStation.getStation();
      if (station != null) {
        fireStationToUpdate.setStation(station);
      }
      fireStationService.saveFireStation(fireStationToUpdate);
      return fireStationToUpdate;
    } else {
      logger.error("The firestation with the address " + address + " doesn't exist");
      return null;
    }
  }


  /**
   * Create - Add a new fire station
   * 
   * @param fire station An object fire station
   * @return The fire station object saved
   */
  @PostMapping("/firestation")
  public FireStation createFireStation(@RequestBody FireStation fireStation) {
    try {
      logger.info(
          "Post request of the endpoint 'firestation' with the firestation : {" + fireStation.toString() + "}");
      FireStation savedFireStation = fireStationService.saveFireStation(fireStation);
      logger.info(
          "response following the Post on the endpoint 'firestation' with the given fireStation : {"
              + fireStation.toString() + "}");
      return savedFireStation;
    } catch (Exception exception) {
      logger.error("Error in the fireStationController in the method createFireStation :"
          + exception.getMessage());
      return null;
    }
  }

}
