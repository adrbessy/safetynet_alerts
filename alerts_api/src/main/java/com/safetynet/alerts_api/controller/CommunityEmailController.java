package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.service.PersonService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityEmailController {

  private static final Logger logger = LogManager.getLogger(CommunityEmailController.class);

  @Autowired
  private PersonService personService;

  @GetMapping("/communityEmail")
  public List<String> getPersonEmailFromCity(@RequestParam String city) {
    logger.info(
        "Get request of the endpoint 'communityEmail' with the city : {" + city + "}");
    List<String> emailList = personService.getPersonEmailFromCity(city);
    return emailList;
  }

}
