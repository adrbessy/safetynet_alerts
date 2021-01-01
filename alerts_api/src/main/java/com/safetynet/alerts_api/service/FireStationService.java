package com.safetynet.alerts_api.service;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
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

  @Autowired
  private PersonRepository personRepository;

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

  public List<Person> getFireStationPersonList(Integer stationNumber) {
    if (stationNumber != null) {
      try {
        // on récupère la liste des stations concernées
        List<FireStation> fireStationList = fireStationRepository.findDistinctByStation(stationNumber);

        // extraction de la liste des adresses des stations
        List<String> addressList = getAddressListFromFireStationList(fireStationList);

        // on récupère la liste des personnes rattachées à la liste des adresses
        return personRepository.findAllByAddressInOrderByAddress(addressList);

      } catch (Exception exception) {
        logger.error("Erreur lors de la récupération des personnes liées à une station de feu : "
            + exception.getMessage() + " Stack Trace : " + exception.getStackTrace());
        return null;
      }
    } else {
      return null;
    }
  }

  private List<String> getAddressListFromFireStationList(List<FireStation> fireStationList) {
    List<String> addressList = new ArrayList<>();
    if (fireStationList != null) {
      fireStationList.forEach(fireStationIterator -> {
        if (fireStationIterator.getAddress() != null && !fireStationIterator.getAddress().isEmpty()) {
          addressList.add(fireStationIterator.getAddress());
        }
      });
    }
    return addressList;
  }

}

