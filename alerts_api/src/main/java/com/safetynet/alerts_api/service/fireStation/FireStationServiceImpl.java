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


  /**
   * Check if the given fire station address exists in the database.
   * 
   * @param address An address
   * @return true if it exists, otherwise returns false
   */
  @Override
  public boolean fireStationAddressExist(String address) {
    logger.debug("in the method fireStationAddressExist in the class FireStationServiceImpl");
    boolean existingFireStationAddress = fireStationRepository.existsByAddress(address);
    return existingFireStationAddress;
  }


  /**
   * Check if the given fire station number exists in the database.
   * 
   * @param stationNumber A firestation number
   * @return true if it exists, otherwise returns false
   */
  @Override
  public boolean fireStationNumberExist(Integer stationNumber) {
    logger.debug("in the method fireStationNumberExist in the class FireStationServiceImpl");
    boolean existingFireStationNumber = fireStationRepository.existsByStation(stationNumber);
    return existingFireStationNumber;
  }


  /**
   * Check if the fire station number in the given list exists in the database.
   * 
   * @param stationNumberList A list of firestation number
   * @return the list of firestation numbers that has not been found
   */
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


  /**
   * Delete a fireStation corresponding to a given id
   * 
   * @param id An id corresponding to the fireStation to delete
   */
  @Override
  public void deleteFireStation(final Long id) {
    logger.debug("in the method deleteFireStation in the class FireStationServiceImpl");
    fireStationRepository.deleteById(id);
  }

  /**
   * Delete a fireStation corresponding to a given address
   * 
   * @param address An address corresponding to the fireStation to delete
   */
  @Override
  public void deleteFireStation(String address) {
    logger.debug("in the method deleteFireStation in the class FireStationServiceImpl");
    fireStationRepository.deleteByAddress(address);
  }

  /**
   * Get a FireStation
   * 
   * @param address An address correspnding to a FireStation to get
   * @return the fireStation if it exists, null if not
   */
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


  /**
   * Save the given fire station
   * 
   * @param fireStation A fire station to save
   * @return The saved firestation
   */
  @Override
  public FireStation saveFireStation(FireStation fireStation) {
    logger.debug("in the method saveFireStation in the class FireStationServiceImpl");
    FireStation savedFireStation = fireStationRepository.save(fireStation);
    return savedFireStation;
  }


  /**
   * Get a list of the fire station numbers from a list of fire stations.
   * 
   * @param fireStationList A list of fire stations
   * @return A list of fire station numbers
   */
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

  /**
   * Save the given fire station list in the data base
   * 
   * @param fireStationsList A list of fire stations to save
   * @return true if everything goes right, otherwise returns false
   */
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

