package com.safetynet.alerts_api.service.phone;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneServiceImpl implements PhoneService {

  private static final Logger logger = LogManager.getLogger(PhoneServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private FireStationRepository firestationRepository;

  @Autowired
  private AddressServiceImpl addressService;

  @Override
  public List<String> getPhoneListFromPersonList(List<Person> personList) {
    logger
        .debug("in the method getPhoneListFromPersonList in the class PhoneServiceImpl");
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

  @Override
  public List<String> getPhoneNumberList(Integer firestation) {
    logger
        .debug("in the method getPhoneNumberList in the class PhoneServiceImpl");
    // we retrieve the list of stations corresponding to the stationNumber
    List<FireStation> fireStationList = firestationRepository.findDistinctByStation(firestation);

    // we retrieve the address list corresponding to the fireStation list
    List<String> addressList = addressService.getAddressListFromFireStationList(fireStationList);

    // we retrieve the person list corresponding to the address list
    List<Person> filteredPersonList = personRepository.findAllByAddressInOrderByAddress(addressList);

    // we retrieve the address list corresponding to the filteredPerson list
    List<String> phoneList = getPhoneListFromPersonList(filteredPersonList);
    LinkedHashSet<String> hashSet = new LinkedHashSet<>(phoneList);
    List<String> listWithoutDuplicates = new ArrayList<>(hashSet);

    return listWithoutDuplicates;
  }


}
