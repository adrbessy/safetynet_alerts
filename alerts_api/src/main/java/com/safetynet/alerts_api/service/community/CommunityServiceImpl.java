package com.safetynet.alerts_api.service.community;

import com.safetynet.alerts_api.model.ChildAlertDTO;
import com.safetynet.alerts_api.model.Fire;
import com.safetynet.alerts_api.model.FireDTO;
import com.safetynet.alerts_api.model.FireDTOByAddress;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.FireStationCommunity;
import com.safetynet.alerts_api.model.FireStationCommunityDTO;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.fireStation.FireStationServiceImpl;
import com.safetynet.alerts_api.service.map.MapService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImpl implements CommunityService {

  private static final Logger logger = LogManager.getLogger(CommunityServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private FireStationRepository firestationRepository;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  @Autowired
  private AddressServiceImpl addressService;

  @Autowired
  private FireStationServiceImpl firestationService;

  @Autowired
  private MapService mapService;

  @Override
  public List<Person> getPersonListFromStationNumber(Integer stationNumber) {
    try {
      // we retrieve the list of stations corresponding to the stationNumber
      List<FireStation> fireStationList = firestationRepository.findDistinctByStation(stationNumber);

      // we retrieve the address list corresponding to the fireStation list
      List<String> addressList = addressService.getAddressListFromFireStationList(fireStationList);

      // we retrieve the person list corresponding to the address list
      List<Person> filteredPersonList = personRepository.findAllByAddressInOrderByAddress(addressList);
      return filteredPersonList;
    } catch (Exception exception) {
      logger
          .error("Error when retrieving the list of persons linked to a fire station number :"
              + exception.getMessage());
      return null;
    }
  }


  @Override
  public FireStationCommunity getPersonNumberInfoListFromStationNumber(Integer stationNumber) {
    if (stationNumber != null) {
      try {
        List<Person> filteredPersonList = getPersonListFromStationNumber(stationNumber);

        // we retrieve the children List and adult List from the filteredPersonList
        List<Person> childrenList = new ArrayList<>();
        List<Person> adultList = new ArrayList<>();
        fullChildrenListAndAdultListFromPersonList(filteredPersonList, childrenList, adultList);

        int child = childrenList.size();
        int adult = adultList.size();

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
  public void fullChildrenListAndAdultListFromPersonList(List<Person> personList, List<Person> childrenList,
      List<Person> adultList) {
    try {
      personList.forEach(personIterator -> {
        addPersonToListFromFireStationList(personIterator,
            medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
                personIterator.getFirstName(), personIterator.getLastName()),
            childrenList,
            adultList, LocalDate.now());
      });
    } catch (Exception exception) {
      logger.error("Error when we try to full children list and adult list :" + exception.getMessage());
    }
  }


  @Override
  public void addPersonToListFromFireStationList(Person personIterator, List<MedicalRecord> medicalRecordList,
      List<Person> childrenList,
      List<Person> adultList, LocalDate currentDate) {
    try {
      medicalRecordList.forEach(medicalRecordIterator -> {
        if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
          personIterator.setAge_Medications_Allergies(medicalRecordIterator, currentDate);
          if (personIterator.getAge() <= 18) {
            childrenList.add(personIterator);
          } else {
            adultList.add(personIterator);
          }
        }
      });
    } catch (Exception exception) {
      logger.error("Error when we try to add persons to list :" + exception.getMessage());
    }
  }


  @Override
  public void setAgeAndMedicationsAndAllergiesFromPersonList(List<Person> personList) {
    try {
      personList.forEach(personIterator -> {
        medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
            personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
              if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
                personIterator.setAge_Medications_Allergies(medicalRecordIterator, LocalDate.now());
              }
            });
      });
    } catch (Exception exception) {
      logger.error("Error when we try to set age, medications and allergies :" + exception.getMessage());
    }
  }


  @Override
  public Home getChildrenListAndAdultListFromAddress(String address) {
    try {

      // we retrieve the list of persons corresponding to the address
      List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);

      // we retrieve the list of children from the list of persons
      List<Person> childrenList = new ArrayList<>();
      List<Person> adultList = new ArrayList<>();
      fullChildrenListAndAdultListFromPersonList(filteredPersonList, childrenList, adultList);

      List<ChildAlertDTO> childrenDTOList = mapService.convertToChildAlertDTOList(childrenList);

      List<ChildAlertDTO> adultDTOList = mapService.convertToChildAlertDTOList(adultList);

      // We create an object including the list of children and the list of adults
      Home home = new Home(childrenDTOList, adultDTOList);
      if (childrenList.isEmpty()) {
        return new Home(new ArrayList<>(), new ArrayList<>());
      }
      return home;
    } catch (Exception exception) {
      logger.error("Error when we try to get ChildrenList And AdultList From an address :" + exception.getMessage());
      return null;
    }
  }


  @Override
  public List<FireDTOByAddress> getPersonInfoByAddressList(List<Integer> stationNumberList) {
    // We create an object including the list of persons and the list of fireStation
    // number deserving the address.
    try {
      List<String> addressList = addressService.getAddressListFromStationNumberList(stationNumberList);

      List<FireDTOByAddress> personInfoByAddressList = new ArrayList<>();
      if (addressList != null) {
        addressList.forEach(addressIterator -> {
          List<Person> personList = getPersonListByAddress(addressIterator);
          List<FireDTO> fireDTOList = mapService.convertToFireDTOList(personList);

          FireDTOByAddress personInfoByAddress = new FireDTOByAddress(addressIterator,
              fireDTOList);
          personInfoByAddressList.add(personInfoByAddress);
        });
      }
      return personInfoByAddressList;
    } catch (

    Exception exception) {
      logger.error("Error when we try to get PersonInfoByAddressList :" + exception.getMessage());
      return null;
    }
  }


  @Override
  public List<Person> getPersonListByFirstNameAndLastName(String firstName, String lastName) {
    try {
      // we retrieve the list of persons corresponding to the address
      List<Person> filteredPersonList = personRepository.findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
      setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList);
      return filteredPersonList;
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList By FirstName And LastName :" + exception.getMessage());
      return null;
    }
  }


  @Override
  public List<Person> getPersonListByLastName(String lastName) {
    try {
      // we retrieve the list of persons corresponding to the address
      List<Person> filteredPersonList = personRepository.findByLastNameAllIgnoreCase(lastName);
      setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList);
      return filteredPersonList;
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList By LastName :" + exception.getMessage());
      return null;
    }
  }


  @Override
  public List<PersonInfoDTO> getPersonListByFirstNameAndLastNameThenOnlyLastName(String firstName, String lastName) {
    try {
      List<Person> personInfoByFirstNameAndLastName = getPersonListByFirstNameAndLastName(firstName, lastName);
      List<Person> personInfoByLastName = getPersonListByLastName(lastName);
      personInfoByLastName.forEach(personIterator -> {
        if (!personInfoByFirstNameAndLastName.contains(personIterator))
          personInfoByFirstNameAndLastName.add(personIterator);
      });

      List<PersonInfoDTO> PersonInfoDTOList = mapService.convertToPersonInfoDTOList(personInfoByFirstNameAndLastName);

      return PersonInfoDTOList;
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList By FirstName And LastName Then Only LastName :"
          + exception.getMessage());
      return null;
    }
  }


  @Override
  public List<Person> getPersonListByAddress(String address) {
    try {
      // we retrieve the list of persons corresponding to the address
      List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);
      setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList);
      return filteredPersonList;
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList By address :"
          + exception.getMessage());
      return null;
    }
  }


  @Override
  public Fire getPersonListWithStationNumber(String address) {
    try {
      List<Person> filteredPersonList = getPersonListByAddress(address);

      List<FireStation> filteredFireStationList = firestationRepository.findDistinctByAddress(address);

      List<Integer> fireStationNumberList = firestationService
          .getStationNumberListFromFireStationList(filteredFireStationList);

      List<FireDTO> fireDTOList = mapService.convertToFireDTOList(filteredPersonList);

      // We create an object including the list of persons and the list of fireStation
      // number deserving the address.
      Fire fire = new Fire(fireDTOList, fireStationNumberList);
      return fire;
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList with a station number :"
          + exception.getMessage());
      return null;
    }
  }


  @Override
  public boolean saveAllPersons(List<Person> personList) {
    if (personList != null) {
      try {
        personRepository.saveAll(personList);
        return true;
      } catch (Exception exception) {
        logger.error("Erreur lors de l'enregistrement de la liste des personnes " + exception.getMessage());
      }
    }
    return false;
  }

}
