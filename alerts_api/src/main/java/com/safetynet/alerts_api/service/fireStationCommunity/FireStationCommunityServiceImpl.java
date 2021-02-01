package com.safetynet.alerts_api.service.fireStationCommunity;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.FireStationCommunity;
import com.safetynet.alerts_api.model.FireStationCommunityDTO;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.childrenAdults.ChildrenAdultsServiceImpl;
import com.safetynet.alerts_api.service.map.MapService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationCommunityServiceImpl implements FireStationCommunityService {

  private static final Logger logger = LogManager.getLogger(FireStationCommunityServiceImpl.class);

  @Autowired
  private MapService mapService;

  @Autowired
  private FireStationRepository firestationRepository;

  @Autowired
  private AddressServiceImpl addressService;

  @Autowired
  private ChildrenAdultsServiceImpl childrenAdultsService;

  @Autowired
  private PersonRepository personRepository;

  @Override
  public FireStationCommunity getPersonNumberInfoListFromStationNumber(Integer stationNumber) {
    if (stationNumber != null) {
      try {
        List<Person> filteredPersonList = getPersonListFromStationNumber(stationNumber);

        // we retrieve the children List and adult List from the filteredPersonList
        List<Person> childrenList = new ArrayList<>();
        List<Person> adultList = new ArrayList<>();
        Map<String, List<Person>> map = childrenAdultsService
            .fullChildrenListAndAdultListFromPersonList(filteredPersonList, childrenList, adultList);
        List<Person> childrenList1 = map.get("childrenList");
        List<Person> adultList1 = map.get("adultList");

        int child = childrenList1.size();
        int adult = adultList1.size();

        // We create an object including the list of persons and the number of adults
        // and children

        List<FireStationCommunityDTO> fireStationCommunityDTOList = mapService.convertToFireStationCommunityDTOList(
            filteredPersonList);
        FireStationCommunity fireStationCommunity = new FireStationCommunity(fireStationCommunityDTOList,
            child, adult);
        return fireStationCommunity;
      } catch (Exception exception) {
        logger
            .error("Error when retrieving the list of information about the persons linked to a fire station number :"
                + exception.getMessage());
        return null;
      }
    } else {
      return null;
    }
  }


  @Override
  public List<Person> getPersonListFromStationNumber(Integer stationNumber) {
    List<Person> filteredPersonList = null;
    try {
      // we retrieve the list of stations corresponding to the stationNumber
      List<FireStation> fireStationList = firestationRepository.findDistinctByStation(stationNumber);

      // we retrieve the address list corresponding to the fireStation list
      List<String> addressList = addressService.getAddressListFromFireStationList(fireStationList);

      // we retrieve the person list corresponding to the address list
      filteredPersonList = personRepository.findAllByAddressInOrderByAddress(addressList);
    } catch (Exception exception) {
      logger
          .error("Error when retrieving the list of persons linked to a fire station number :"
              + exception.getMessage());
    }
    return filteredPersonList;
  }


}
