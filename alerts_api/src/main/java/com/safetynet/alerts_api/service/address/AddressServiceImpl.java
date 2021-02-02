package com.safetynet.alerts_api.service.address;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.repository.FireStationRepository;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  private static final Logger logger = LogManager.getLogger(AddressServiceImpl.class);

  @Autowired
  private FireStationRepository firestationRepository;

  @Override
  public List<String> getAddressListFromStationNumberList(List<Integer> stationNumberList) {
    logger.debug("in the method getAddressListFromStationNumberList in the class AddressServiceImpl");
    List<String> addressList = new ArrayList<>();
    if (stationNumberList != null) {
      stationNumberList.forEach(stationIterator -> {
        // we retrieve the list of stations corresponding to the stationNumber
        addAddressToListFromFireStationList(firestationRepository.findDistinctByStation(stationIterator), addressList);
      });
    }
    LinkedHashSet<String> hashSet = new LinkedHashSet<>(addressList);
    List<String> listWithoutDuplicates = new ArrayList<>(hashSet);
    return listWithoutDuplicates;
  }

  @Override
  public void addAddressToListFromFireStationList(List<FireStation> fireStationList, List<String> addressList) {
    logger.debug("in the method addAddressToListFromFireStationList in the class AddressServiceImpl");
    // we retrieve the list of stations corresponding to the stationNumber
    fireStationList.forEach(fireStationIterator -> {
      if (fireStationIterator.getAddress() != null && !fireStationIterator.getAddress().isEmpty()) {
        addressList.add(fireStationIterator.getAddress());
      }
    });
  }


  @Override
  public List<String> getAddressListFromFireStationList(List<FireStation> fireStationList) {
    logger.debug("in the method getAddressListFromFireStationList in the class AddressServiceImpl");
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
