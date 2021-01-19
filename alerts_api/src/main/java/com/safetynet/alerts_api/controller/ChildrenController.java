package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.service.person.PersonService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildrenController {

  private static final Logger logger = LogManager.getLogger(ChildrenController.class);

  @Autowired
  private PersonService personService;


  /**
   * Read - Get a children list (inferior or equal 18) living to a particular
   * address, with a list of other people living there. If no children living
   * there, returns an empty list
   * 
   * @param an address
   * @return - A List of Home
   */
  @GetMapping("/childAlert")
  public String getChildListLivingToThisAdress(@RequestParam String address) {
    logger.info(
        "Get request of the endpoint 'childAlert' with the address : {" + address + "}");
    List<Home> homeList = personService.getChildrenListAndAdultListFromAddress(address);
    logger.info("response following the Get on the endpoint 'childAlert' with the given address : {" + address + "}");
    ObjectMapper mapper = new ObjectMapper();
    try {
      String normalView = mapper.writerWithView(ChildrenController.class)
          .writeValueAsString(homeList);
      return normalView;
    } catch (JsonProcessingException e) {
      logger.error("Unable to process the filter in getChildListLivingToThisAdress: ", e);
      return null;
    }
  }

}
