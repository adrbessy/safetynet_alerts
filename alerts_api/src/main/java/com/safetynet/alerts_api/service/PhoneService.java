package com.safetynet.alerts_api.service;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private FireStationRepository firestationRepository;

  @Autowired
  private AddressService addressService;

  private List<String> getPhoneListFromPersonList(List<Person> personList) {
    List<String> phoneList = new ArrayList<>();
    if (personList != null) {
      personList.forEach(personIterator -> {
        if (personIterator.getPhone() != null && !personIterator.getPhone().isEmpty()) {
          phoneList.add(personIterator.getPhone());
        }
      });
    }
    return phoneList;
  }

  public List<String> getPhoneNumberList(Integer firestation) {
    // we retrieve the list of stations corresponding to the stationNumber
    List<FireStation> fireStationList = firestationRepository.findDistinctByStation(firestation);

    // we retrieve the address list corresponding to the fireStation list
    List<String> addressList = addressService.getAddressListFromFireStationList(fireStationList);

    // we retrieve the person list corresponding to the address list
    List<Person> filteredPersonList = personRepository.findAllByAddressInOrderByAddress(addressList);

    // we retrieve the address list corresponding to the filteredPerson list
    List<String> phoneList = getPhoneListFromPersonList(filteredPersonList);

    List<String> phoneListNoDuplicates = phoneList.stream().distinct().collect(Collectors.toList());

    return phoneListNoDuplicates;
  }


}
