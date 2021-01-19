package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts_api.model.PersonNumberInfo;
import com.safetynet.alerts_api.service.person.PersonService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationCommunityController {

  private static final Logger logger = LogManager.getLogger(FireStationCommunityController.class);

  @Autowired
  private PersonService personService;

  /**
   * Read - Get a person list covered by a given fire station with the number of
   * occurrences of children and adults.
   * 
   * @param a fire station number
   * @return A List of persons covered by a FireStation and the number of children
   *         and adults
   */
  @GetMapping("/firestation")
  public String getPersonListCoveredByThisStation(@RequestParam Integer stationNumber) {
    logger.info(
        "Get request of the endpoint 'fireStation' with the stationNumber : {" + stationNumber.toString() + "}");
    List<PersonNumberInfo> personNumberInfoList = personService
        .getPersonNumberInfoListFromStationNumber(stationNumber);
    ObjectMapper mapper = new ObjectMapper();
    try {
      String normalView = mapper.writerWithView(FireStationCommunityController.class)
          .writeValueAsString(personNumberInfoList);
      return normalView;
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

}
