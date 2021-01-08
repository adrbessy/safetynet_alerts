package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts_api.model.FireStationInfo;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfo;
import com.safetynet.alerts_api.model.PersonInfoByAddress;
import com.safetynet.alerts_api.service.FireStationService;
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

  private static final Logger logger = LogManager.getLogger(PersonInfoController.class);

  @Autowired
  private PersonService personService;

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

  @GetMapping("/childAlert")
  public MappingJacksonValue getChildListLivingToThisAdress(@RequestParam String address) {
    logger.info(
        "Get request of the endpoint 'childAlert' with the address : {" + address + "}");
    List<Home> homeList = personService.getChildrenListAndAdultListFromAddress(address);
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName", "age");
    FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
    MappingJacksonValue filteredFireStationPersonList = new MappingJacksonValue(homeList);
    filteredFireStationPersonList.setFilters(filterList);
    return filteredFireStationPersonList;
  }

  @GetMapping("/fire")
  public MappingJacksonValue getPersonListLivingToThisAdressAndFirestationNumber(@RequestParam String address) {
    logger.info(
        "Get request of the endpoint 'childAlert' with the address : {" + address + "}");
    List<PersonInfo> personList = personService.getPersonListWithStationNumber(address);
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "phone", "age",
        "medications", "allergies");
    FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
    MappingJacksonValue filteredFireStationPersonList = new MappingJacksonValue(personList);
    filteredFireStationPersonList.setFilters(filterList);
    return filteredFireStationPersonList;
  }

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
