package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
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
    logger.info(
        "Delete request of the endpoint 'firestation' with the firestation Id : {" + id.toString() + "}");
    fireStationService.deleteFireStation(id);
  }


  /**
   * Update - Update an existing fire station
   * 
   * @param id     - The id of the firestation to update
   * @param person - The fire station object updated
   * @return
   */
  @PutMapping("/firestation/{id}")
  public MappingJacksonValue updateFireStation(@PathVariable("id") final Long id,
      @RequestBody FireStation fireStation) {
    logger.info(
        "Put request of the endpoint 'firestation' with the firestation Id : {" + id.toString() + "}");
    FireStation fireStationToUpdate = fireStationService.getFireStation(id);
    if (fireStationToUpdate != null) {
      Integer station = fireStation.getStation();
      if (station != null) {
        fireStationToUpdate.setStation(station);
      }
      fireStationService.saveFireStation(fireStationToUpdate);
      SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "address", "station");
      FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
      MappingJacksonValue filteredFireStationList = new MappingJacksonValue(fireStationToUpdate);
      filteredFireStationList.setFilters(filterList);
      return filteredFireStationList;
    } else {
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
  public MappingJacksonValue createFireStation(@RequestBody FireStation fireStation) {
    logger.info(
        "Post request of the endpoint 'firestation' with the firestation : {" + fireStation.toString() + "}");
    FireStation savedFireStation = fireStationService.saveFireStation(fireStation);
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "firstName", "lastName",
        "address",
        "phone");
    FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
    MappingJacksonValue filteredFireStationList = new MappingJacksonValue(savedFireStation);
    filteredFireStationList.setFilters(filterList);
    return filteredFireStationList;
  }

}
