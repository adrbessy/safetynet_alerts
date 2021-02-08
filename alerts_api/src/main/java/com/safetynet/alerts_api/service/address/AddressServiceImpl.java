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

  /**
   * Get the List of address of persons covered by a fire station number list.
   * 
   * @param stationNumberList A list of fire station numbers
   * @return A List of address without duplicates
   */
  @Override
  public List<String> getAddressListFromStationNumberList(List<Integer> stationNumberList) {
    logger.debug("execution start of the getAddressListFromStationNumberList method in AddressServiceImpl");
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

  /**
   * Add the address of persons to a list from FireStation list.
   * 
   * @param fireStationList A List of FireStation
   * @param addressList     A (probably empty) List of address
   */
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

  /**
   * Get the List of address of persons covered by a fire station list.
   * 
   * @param fireStationList A List of FireStation
   * @return A List of address
   */
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
