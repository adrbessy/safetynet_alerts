package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts_api.model.FireStationInfo;
import com.safetynet.alerts_api.service.FireStationService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

  private static final Logger logger = LogManager.getLogger(FireStationController.class);

  @Autowired
  private FireStationService fireStationService;

  @GetMapping("/firestation")
  public MappingJacksonValue getPersonListCoveredByThisStation(@RequestParam Integer stationNumber) {
    logger.info(
        "Get request of the endpoint 'fireStation' with the stationNumber : {" + stationNumber.toString() + "}");
    List<FireStationInfo> FireStationPersonList = fireStationService.getFireStationPersonList(stationNumber);
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName", "address",
        "phone");
    FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
    MappingJacksonValue filteredFireStationPersonList = new MappingJacksonValue(FireStationPersonList);
    filteredFireStationPersonList.setFilters(filterList);
    return filteredFireStationPersonList;
  }

}
