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
  public List<String> getAddressListFromStationNumberList(List<Integer> stationNumberList) {
    List<String> addressList = new ArrayList<>();
    if (stationNumberList != null) {
      stationNumberList.forEach(stationIterator -> {
        // we retrieve the list of stations corresponding to the stationNumber
        addAddressToListFromFireStationList(firestationRepository.findDistinctByStation(stationIterator), addressList);
      });
    }
    ;
    List<String> addressListNoDuplicates = addressList.stream().distinct().collect(Collectors.toList());
    return addressListNoDuplicates;
  }

  @Override
  public void addAddressToListFromFireStationList(List<FireStation> fireStationList, List<String> addressList) {
    // we retrieve the list of stations corresponding to the stationNumber
    fireStationList.forEach(fireStationIterator -> {
      if (fireStationIterator.getAddress() != null && !fireStationIterator.getAddress().isEmpty()) {
        addressList.add(fireStationIterator.getAddress());
      }
    });
  }


  @Override
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
