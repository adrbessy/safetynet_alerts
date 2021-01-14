package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

  @Autowired
  private FireStationService fireStationService;

  /**
   * Create - Add a new fire station
   * 
   * @param fire station An object fire station
   * @return The fire station object saved
   */
  @PostMapping("/firestation")
  public MappingJacksonValue createFireStation(@RequestBody FireStation fireStation) {
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
