package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.service.FireStationService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

  private static final Logger logger = LogManager.getLogger(FireStationController.class);

  @Autowired
  private FireStationService fireStationService;

  @GetMapping("/firestation")
  public List<Person> getPersonListCoveredByThisStation(@RequestParam Integer stationNumber) {
    logger.info(
        "Requête Get sur le endpoint 'fireStation' avec stationNumber : {" + stationNumber.toString() + "} reçue");
    // FireStationPersonList fireStationPersonList =
    // fireStationService.getFireStationPersonList(stationNumber);

    logger.info("Réponse suite au Get sur le endpoint 'fireStation' avec stationNumber : {" + stationNumber.toString()
        + "} transmise");
    return fireStationService.getFireStationPersonList(stationNumber);

  }

}
