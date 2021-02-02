package com.safetynet.alerts_api.service.fireStation;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.repository.FireStationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationServiceImpl implements FireStationService {

  private static final Logger logger = LogManager.getLogger(FireStationServiceImpl.class);

  @Autowired
  private FireStationRepository fireStationRepository;


  @Override
  public boolean fireStationAddressExist(String address) {
    logger.debug("in the method fireStationAddressExist in the class FireStationServiceImpl");
    boolean existingFireStationAddress = fireStationRepository.existsByAddress(address);
    return existingFireStationAddress;
  }


  @Override
  public boolean fireStationNumberExist(Integer stationNumber) {
    logger.debug("in the method fireStationNumberExist in the class FireStationServiceImpl");
    boolean existingFireStationNumber = fireStationRepository.existsByStation(stationNumber);
    return existingFireStationNumber;
  }

  @Override
  public List<Integer> fireStationNumberListExist(List<Integer> stationNumberList) {
    logger.debug("in the method fireStationNumberListExist in the class FireStationServiceImpl");
    List<Integer> FireStationNumberNotFound = new ArrayList<>();
    stationNumberList.forEach(stationIterator -> {
      if (!fireStationRepository.existsByStation(stationIterator)) {
        FireStationNumberNotFound.add(stationIterator);
      }
    });
    return FireStationNumberNotFound;
  }


  @Override
  public void deleteFireStation(final Long id) {
    logger.debug("in the method deleteFireStation in the class FireStationServiceImpl");
    fireStationRepository.deleteById(id);
  }

  @Override
  public void deleteFireStation(String address) {
    logger.debug("in the method deleteFireStation in the class FireStationServiceImpl");
    fireStationRepository.deleteByAddress(address);
  }


  @Override
  public FireStation getFireStation(final String address) {
    logger.debug("in the method getFireStation in the class FireStationServiceImpl");
    Optional<FireStation> fireStation = fireStationRepository.findByAddress(address);
    if (fireStation.isPresent()) {
      FireStation fireStationToUpdate = fireStation.get();
      return fireStationToUpdate;
    } else {
      return null;
    }
  }


  @Override
  public FireStation saveFireStation(FireStation fireStation) {
    logger.debug("in the method saveFireStation in the class FireStationServiceImpl");
    FireStation savedFireStation = fireStationRepository.save(fireStation);
    return savedFireStation;
  }


  @Override
  public List<Integer> getStationNumberListFromFireStationList(List<FireStation> fireStationList) {
    logger.debug("in the method getStationNumberListFromFireStationList in the class FireStationServiceImpl");
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


  @Override
  public boolean saveAllFireStations(List<FireStation> fireStationsList) {
    logger.debug("in the method saveAllFireStations in the class FireStationServiceImpl");
    if (fireStationsList != null && !fireStationsList.isEmpty()) {
      try {
        fireStationRepository.saveAll(fireStationsList);
        return true;
      } catch (Exception exception) {
        logger.error("Erreur lors de l'enregistrement de la liste des personnes " + exception.getMessage());
      }
    }
    return false;
  }
}

