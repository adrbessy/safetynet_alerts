package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.service.person.PersonService;
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
  public Home getChildListLivingToThisAdress(@RequestParam String address) {
    logger.info(
        "Get request of the endpoint 'childAlert' with the address : {" + address + "}");
    Home home = personService.getChildrenListAndAdultListFromAddress(address);
    logger.info("response following the Get on the endpoint 'childAlert' with the given address : {" + address + "}");

    /*
     * ObjectMapper mapper = new ObjectMapper(); try { String normalView =
     * mapper.writerWithView(ChildrenController.class) .writeValueAsString(home);
     * System.out.println("normalView : " + normalView); String result =
     * normalView.substring(1, normalView.length() - 1); JSONParser parser = new
     * JSONParser(); JSONObject json = (JSONObject) parser.parse(result); return
     * json; } catch (JsonProcessingException e) { logger.
     * error("Unable to process the filter in getChildListLivingToThisAdress: ", e);
     * return null; } catch (ParseException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); return null; }
     */
    return home;
  }

}
