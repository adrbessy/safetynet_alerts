package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts_api.model.PersonInfoByAddress;
import com.safetynet.alerts_api.service.PersonService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FloodController {

  private static final Logger logger = LogManager.getLogger(FireStationController.class);

  @Autowired
  private PersonService personService;

  @GetMapping("/flood")
  public MappingJacksonValue getAddressCoveredByTheseStation(@RequestParam List<Integer> stations) {
    logger.info(
        "Get request of the endpoint 'phoneAlert' with the firestationNumber : {" + stations.toString() + "}");
    List<PersonInfoByAddress> personInfoByaddressList = personService.getPersonInfoByAddressList(stations);
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "phone", "age",
        "medications", "allergies");
    FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
    MappingJacksonValue filteredFireStationPersonList = new MappingJacksonValue(personInfoByaddressList);
    filteredFireStationPersonList.setFilters(filterList);
    return filteredFireStationPersonList;
  }

}
