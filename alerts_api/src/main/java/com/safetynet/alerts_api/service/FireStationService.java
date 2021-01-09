package com.safetynet.alerts_api.service;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.repository.FireStationRepository;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationService {

  private static final Logger logger = LogManager.getLogger(FireStationService.class);

  @Autowired
  private FireStationRepository fireStationRepository;


  public List<Integer> getStationNumberListFromFireStationList(List<FireStation> fireStationList) {
    List<Integer> fireStationNumberList = new ArrayList<>();
    if (fireStationList != null) {
      fireStationList.forEach(fireStationIterator -> {
        if (fireStationIterator.getStation() != null) {
          fireStationNumberList.add(fireStationIterator.getStation());
        }
      });
    }
    return fireStationNumberList;
  }


  /**
   * Sauvegarder la liste des stations de feu
   *
   * @param fireStationsList Liste à sauvegarder
   * @return true si la sauvegarde s'est bien passée, false en cas d'erreur
   */
  public boolean saveAllFireStations(List<FireStation> fireStationsList) {
    if (fireStationsList != null && !fireStationsList.isEmpty()) {
      try {
        fireStationRepository.saveAll(fireStationsList);
        return true;
      } catch (Exception exception) {
        logger.error("Erreur lors de l'enregistrement de la liste des personnes " + exception.getMessage()
            + " , Stack Trace : " + exception.getStackTrace());
      }
    }
    return false;
  }








}

