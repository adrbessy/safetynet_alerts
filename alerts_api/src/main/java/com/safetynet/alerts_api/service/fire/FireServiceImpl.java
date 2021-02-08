package com.safetynet.alerts_api.service.fire;

import com.safetynet.alerts_api.model.Fire;
import com.safetynet.alerts_api.model.FireDTO;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import com.safetynet.alerts_api.service.home.HomeService;
import com.safetynet.alerts_api.service.map.MapService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireServiceImpl implements FireService {

  private static final Logger logger = LogManager.getLogger(FireServiceImpl.class);

  @Autowired
  private FireStationRepository firestationRepository;

  @Autowired
  private FireStationService firestationService;

  @Autowired
  private HomeService homeService;

  @Autowired
  private MapService mapService;

  /**
   * Get a Person List with the number of fire station from an address.
   * 
   * @param address An address
   * @return A Fire object including a list of persons with the associated
   *         firestation number
   */
  @Override
  public Fire getPersonListWithStationNumber(String address) {
    logger.debug("in the method getPersonListWithStationNumber in the class FireServiceImpl");
    Fire fire = null;
    try {
      List<Person> filteredPersonList = homeService.getPersonListByAddress(address);
      List<FireStation> filteredFireStationList = firestationRepository.findDistinctByAddress(address);
      List<Integer> fireStationNumberList = firestationService
          .getStationNumberListFromFireStationList(filteredFireStationList);
      List<FireDTO> fireDTOList = mapService.convertToFireDTOList(filteredPersonList);
      // We create an object including the list of persons and the list of fireStation
      // number deserving the address.
      fire = new Fire(fireDTOList, fireStationNumberList);
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList with a station number :"
          + exception.getMessage());
    }
    return fire;
  }

}
