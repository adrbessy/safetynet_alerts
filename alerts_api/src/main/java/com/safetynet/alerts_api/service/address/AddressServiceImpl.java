package com.safetynet.alerts_api.service.address;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.repository.FireStationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  @Autowired
  private FireStationRepository firestationRepository;

  @Override
  public List<String> getAddressListFromStationNumberList(List<Integer> stationsList) {
    List<String> addressList = new ArrayList<>();
    if (stationsList != null) {
      stationsList.forEach(stationIterator -> {
        // we retrieve the list of stations corresponding to the stationNumber
        firestationRepository.findDistinctByStation(stationIterator).forEach(fireStationIterator -> {
          if (fireStationIterator.getAddress() != null && !fireStationIterator.getAddress().isEmpty()) {
            addressList.add(fireStationIterator.getAddress());
          }
        });
      });
    }
    ;
    List<String> addressListNoDuplicates = addressList.stream().distinct().collect(Collectors.toList());
    System.out.println("phoneListNoDuplicates:" + addressListNoDuplicates);
    return addressListNoDuplicates;
  }

  public List<String> getAddressListFromFireStationList(List<FireStation> fireStationList) {
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
