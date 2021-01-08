package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts_api.model.Person;
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
public class PersonInfoController {

  private static final Logger logger = LogManager.getLogger(FireStationController.class);

  @Autowired
  private PersonService personService;

  @GetMapping("/personInfo")
  public MappingJacksonValue getAddressCoveredByTheseStation(@RequestParam String firstName,
      @RequestParam String lastName) {
    logger.info(
        "Get request of the endpoint 'personInfo' with the first name : {" + firstName + "} and the last name : "
            + lastName);
    List<Person> personInfoByaddressList = personService.getPersonInfoByFirstNameAndLastNameThenOnlyLastName(firstName,
        lastName);
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName", "address",
        "age", "email",
        "medications", "allergies");
    FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
    MappingJacksonValue filteredFireStationPersonList = new MappingJacksonValue(personInfoByaddressList);
    filteredFireStationPersonList.setFilters(filterList);
    return filteredFireStationPersonList;
  }

}
