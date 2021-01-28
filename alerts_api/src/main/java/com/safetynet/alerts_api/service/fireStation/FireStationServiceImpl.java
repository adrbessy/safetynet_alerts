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
  public boolean fireStationNumberExist(Integer stationNumber) {
    boolean existingFireStationNumber = fireStationRepository.existsByStation(stationNumber);
    return existingFireStationNumber;
  }

  @Override
  public List<Integer> fireStationNumberListExist(List<Integer> stationNumberList) {
    List<Integer> FireStationNumberNotFound = new ArrayList<>();
    stationNumberList.forEach(stationIterator -> {
      if (!fireStationRepository.existsByStation(stationIterator)) {
        FireStationNumberNotFound.add(stationIterator);
      }
    });
    return FireStationNumberNotFound;
  }


  @Override
  public boolean deleteFireStation(String address) {
    List<FireStation> existingFireStationList = fireStationRepository.findDistinctByAddressIgnoreCase(address);
    if (existingFireStationList.isEmpty()) {
      return false;
    } else {
      fireStationRepository.deleteByAddress(address);
      return true;
    }
  }


  @Override
  public FireStation getFireStation(final String address) {
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
    FireStation savedFireStation = fireStationRepository.save(fireStation);
    return savedFireStation;
  }


  @Override
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


  @Override
  public boolean saveAllFireStations(List<FireStation> fireStationsList) {
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

