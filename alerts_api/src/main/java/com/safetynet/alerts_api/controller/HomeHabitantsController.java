package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.exceptions.NonexistentException;
import com.safetynet.alerts_api.model.Fire;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.service.childAlert.ChildAlertService;
import com.safetynet.alerts_api.service.fire.FireService;
import com.safetynet.alerts_api.service.person.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeHabitantsController {

  private static final Logger logger = LogManager.getLogger(HomeHabitantsController.class);

  @Autowired
  private ChildAlertService childAlertService;

  @Autowired
  private FireService fireService;

  @Autowired
  private PersonService personService;

  /**
   * Get a person list living to a particular address, with a the number of the
   * fire station deserving it.
   * 
   * @param address The given address
   * @return A Fire object : a List of persons living at a given address with a
   *         the number of the fire station deserving it
   */
  @GetMapping("/fire")
  public Fire getFire(@RequestParam String address) {
    Fire fire = null;
    boolean existingPersonAddress = false;
    try {
      logger.info(
          "Get request of the endpoint 'childAlert' with the address : {" + address + "}");
      existingPersonAddress = personService.personAddressExist(address);
      if (existingPersonAddress) {
        fire = fireService.getPersonListWithStationNumber(address);
        logger.info(
            "response following the Get on the endpoint 'fire' with the given address : {"
                + address + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the HomeController in the method getFire :"
          + exception.getMessage());
    }
    if (!existingPersonAddress) {
      logger.error("The person address " + address + " doesn't exist.");
      throw new NonexistentException(
          "The person address " + address + " doesn't exist.");
    }
    return fire;
  }

  /**
   * Get a children list (inferior or equal 18) living to a particular address,
   * with a list of other people living there. If no children living there,
   * returns an empty list
   * 
   * @param address The given address
   * @return A Home object : a List of children and adults living at a given
   *         address
   */
  @GetMapping("/childAlert")
  public Home getHome(@RequestParam String address) {
    boolean existingPersonAddress = false;
    Home home = null;
    try {
      logger.info(
          "Get request of the endpoint 'childAlert' with the address : {" + address + "}");
      existingPersonAddress = personService.personAddressExist(address);
      if (existingPersonAddress) {
        home = childAlertService.getChildrenListAndAdultListFromAddress(address);
        logger
            .info("response following the Get on the endpoint 'childAlert' with the given address : {" + address + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the HomeController in the method getHome :"
          + exception.getMessage());
    }
    if (!existingPersonAddress) {
      logger.error("The person address " + address + " doesn't exist.");
      throw new NonexistentException(
          "The person address " + address + " doesn't exist.");
    }
    return home;
  }

}
