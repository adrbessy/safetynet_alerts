package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfo;
import com.safetynet.alerts_api.model.PersonInfoByAddress;
import com.safetynet.alerts_api.model.PersonNumberInfo;
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

  /**
   * Read - Get a person list covered by a given fire station with the number of
   * occurrences of children and adults.
   * 
   * @param a fire station number
   * @return - A List of FireStationInfo
   */
  @GetMapping("/firestation")
  public MappingJacksonValue getPersonListCoveredByThisStation(@RequestParam Integer stationNumber) {
    logger.info(
        "Get request of the endpoint 'fireStation' with the stationNumber : {" + stationNumber.toString() + "}");
    List<PersonNumberInfo> FireStationPersonList = personService.getPersonNumberList(stationNumber);
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName", "address",
        "phone");
    FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
    MappingJacksonValue filteredFireStationPersonList = new MappingJacksonValue(FireStationPersonList);
    filteredFireStationPersonList.setFilters(filterList);
    return filteredFireStationPersonList;
  }

  /**
   * Read - Get a children list (inferior or equal 18) living to a particular
   * address, with a list of other people living there. If no children living
   * there, returns an empty list
   * 
   * @param an address
   * @return - A List of Home
   */
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

  /**
   * Read - Get a person list living to a particular address, with a the number of
   * the fire station deserving it.
   * 
   * @param an address
   * @return - A List of PersonInfo
   */
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

  /**
   * Read - Get a person list grouped by address and grouped by the number of the
   * fire station deserving it.
   * 
   * @param a List of number of fire station
   * @return - A List of PersonInfoByAddress
   */
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

  /**
   * Read - Get the information about a person from his first name and his last
   * name; get also the information about the persons having the same last name.
   * 
   * @param a List of number of fire station
   * @return - A List of Person
   */
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
