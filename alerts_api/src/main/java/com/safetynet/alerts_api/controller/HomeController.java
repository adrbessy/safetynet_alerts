package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.PersonInfo;
import com.safetynet.alerts_api.service.person.PersonService;
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
   * @return - A PersonInfo object
   */
  @GetMapping("/fire")
  public PersonInfo getPersonInfo(@RequestParam String address) {
    logger.info(
        "Get request of the endpoint 'childAlert' with the address : {" + address + "}");
    PersonInfo personInfo = personService.getPersonListWithStationNumber(address);
    logger.info(
        "response following the Get on the endpoint 'fire' with the given address : {"
            + address + "}");
    return personInfo;
  }

  /**
   * Read - Get a children list (inferior or equal 18) living to a particular
   * address, with a list of other people living there. If no children living
   * there, returns an empty list
   * 
   * @param an address
   * @return - A Home : a List of children and adults living at a given address
   */
  @GetMapping("/childAlert")
  public Home getChildListLivingToThisAdress(@RequestParam String address) {
    logger.info(
        "Get request of the endpoint 'childAlert' with the address : {" + address + "}");
    Home home = personService.getChildrenListAndAdultListFromAddress(address);
    logger.info("response following the Get on the endpoint 'childAlert' with the given address : {" + address + "}");
    return home;
  }

}
