package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts_api.model.PersonInfo;
import com.safetynet.alerts_api.model.PersonInfoByAddress;
import com.safetynet.alerts_api.service.person.PersonService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  private static final Logger logger = LogManager.getLogger(HomeController.class);

  @Autowired
  private PersonService personService;

  /**
   * Read - Get a person list living to a particular address, with a the number of
   * the fire station deserving it.
   * 
   * @param an address
   * @return - A List of PersonInfo
   */
  @GetMapping("/fire")
  public String getPersonListLivingToThisAdressAndFirestationNumber(@RequestParam String address) {
    logger.info(
        "Get request of the endpoint 'childAlert' with the address : {" + address + "}");
    List<PersonInfo> personList = personService.getPersonListWithStationNumber(address);
    /*
     * SimpleBeanPropertyFilter filter =
     * SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "phone", "age",
     * "medications", "allergies"); FilterProvider filterList = new
     * SimpleFilterProvider().addFilter("dynamicFilter", filter);
     * MappingJacksonValue filteredFireStationPersonList = new
     * MappingJacksonValue(personList);
     * filteredFireStationPersonList.setFilters(filterList);
     */
    ObjectMapper mapper = new ObjectMapper();
    try {
      String normalView = mapper.writerWithView(HomeController.class)
          .writeValueAsString(personList);
      return normalView;
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Read - Get a person list grouped by address and grouped by the number of the
   * fire station deserving it.
   * 
   * @param a List of number of fire station
   * @return - A List of PersonInfoByAddress
   */
  @GetMapping("/flood")
  public String getAddressCoveredByTheseStation(@RequestParam List<Integer> stations) {
    logger.info(
        "Get request of the endpoint 'phoneAlert' with the firestationNumber : {" + stations.toString() + "}");
    List<PersonInfoByAddress> personInfoByaddressList = personService.getPersonInfoByAddressList(stations);
    /*
     * SimpleBeanPropertyFilter filter =
     * SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "phone", "age",
     * "medications", "allergies"); FilterProvider filterList = new
     * SimpleFilterProvider().addFilter("dynamicFilter", filter);
     * MappingJacksonValue filteredFireStationPersonList = new
     * MappingJacksonValue(personInfoByaddressList);
     * filteredFireStationPersonList.setFilters(filterList);
     */
    ObjectMapper mapper = new ObjectMapper();
    try {
      String normalView = mapper.writerWithView(HomeController.class)
          .writeValueAsString(personInfoByaddressList);
      return normalView;
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

}
