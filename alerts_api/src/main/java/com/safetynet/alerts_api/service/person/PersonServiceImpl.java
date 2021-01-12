package com.safetynet.alerts_api.service.person;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfo;
import com.safetynet.alerts_api.model.PersonInfoByAddress;
import com.safetynet.alerts_api.model.PersonNumberInfo;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.fireStation.FireStationServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

  private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);

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

  public List<Person> getPersonListFromStationNumber(Integer stationNumber) {
    // we retrieve the list of stations corresponding to the stationNumber
    List<FireStation> fireStationList = firestationRepository.findDistinctByStation(stationNumber);

    // we retrieve the address list corresponding to the fireStation list
    List<String> addressList = addressService.getAddressListFromFireStationList(fireStationList);

    // we retrieve the person list corresponding to the address list
    List<Person> filteredPersonList = personRepository.findAllByAddressInOrderByAddress(addressList);
    return filteredPersonList;
  }

  @Override
  public List<PersonNumberInfo> getPersonNumberInfoListFromStationNumber(Integer stationNumber) {
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
        PersonNumberInfo personNumberInfo = new PersonNumberInfo(filteredPersonList,
            child, adult);
        List<PersonNumberInfo> personNumberInfoList = new ArrayList<>();
        personNumberInfoList.add(personNumberInfo);

        return personNumberInfoList;

      } catch (Exception exception) {
        logger.error("Erreur lors de la récupération des personnes liées à une station de feu : "
            + exception.getMessage() + " Stack Trace : " + exception.getStackTrace());
        return null;
      }
    } else {
      return null;
    }
  }


  @Override
  public void fullChildrenListAndAdultListFromPersonList(List<Person> personList, List<Person> childrenList,
      List<Person> adultList) {
    personList.forEach(personIterator -> {
      addAddressToListFromFireStationList(personIterator,
          medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
              personIterator.getFirstName(), personIterator.getLastName()),
          childrenList,
          adultList, LocalDate.now());
    });
  }

  public void addAddressToListFromFireStationList(Person personIterator, List<MedicalRecord> medicalRecordList,
      List<Person> childrenList,
      List<Person> adultList, LocalDate currentDate) {
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
  }


  @Override
  public List<Home> getChildrenListAndAdultListFromAddress(String address) {

    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);

    // we retrieve the list of children from the list of persons
    List<Person> childrenList = new ArrayList<>();
    List<Person> adultList = new ArrayList<>();
    fullChildrenListAndAdultListFromPersonList(filteredPersonList, childrenList, adultList);
    System.out.println("childrenList : " + childrenList);

    // We create an object including the list of children and the list of adults
    Home home = new Home(childrenList, adultList);
    if (childrenList.isEmpty()) {
      return new ArrayList<>();
    }
    List<Home> homeList = new ArrayList<>();
    homeList.add(home);

    return homeList;
  }


  @Override
  public List<PersonInfoByAddress> getPersonInfoByAddressList(List<Integer> stationsList) {
    // We create an object including the list of persons and the list of fireStation
    // number deserving the address.
    List<PersonInfoByAddress> personInfoByAddressList = new ArrayList<>();
    List<String> addressList = addressService.getAddressListFromStationNumberList(stationsList);
    if (addressList != null) {
      addressList.forEach(addressIterator -> {
        PersonInfoByAddress personInfoByAddress = new PersonInfoByAddress(addressIterator,
            getPersonListByAddress(addressIterator));
        personInfoByAddressList.add(personInfoByAddress);
      });
    }
    return personInfoByAddressList;
  }


  @Override
  public List<Person> getPersonListByFirstNameAndLastName(String firstName, String lastName) {
    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    filteredPersonList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
            if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
              personIterator.setAge_Medications_Allergies(medicalRecordIterator, LocalDate.now());
            }
          });
    });
    return filteredPersonList;
  }


  @Override
  public List<Person> getPersonListByLastName(String lastName) {
    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findByLastNameAllIgnoreCase(lastName);
    filteredPersonList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
            if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
              personIterator.setAge_Medications_Allergies(medicalRecordIterator, LocalDate.now());
            }
          });
    });
    return filteredPersonList;
  }


  @Override
  public List<Person> getPersonListByFirstNameAndLastNameThenOnlyLastName(String firstName, String lastName) {
    List<Person> personInfoByFirstNameAndLastName = getPersonListByFirstNameAndLastName(firstName, lastName);
    List<Person> personInfoByLastName = getPersonListByLastName(lastName);
    personInfoByLastName.forEach(personIterator -> {
      if (!personInfoByFirstNameAndLastName.contains(personIterator))
        personInfoByFirstNameAndLastName.add(personIterator);
    });
    return personInfoByFirstNameAndLastName;
  }


  @Override
  public List<Person> getPersonListByAddress(String address) {

    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);

    filteredPersonList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
            if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
              personIterator.setAge_Medications_Allergies(medicalRecordIterator, LocalDate.now());
            }
          });
    });
    return filteredPersonList;
  }


  @Override
  public List<PersonInfo> getPersonListWithStationNumber(String address) {

    List<Person> filteredPersonList = getPersonListByAddress(address);

    List<FireStation> filteredFireStationList = firestationRepository.findDistinctByAddress(address);

    List<Integer> fireStationNumberList = firestationService
        .getStationNumberListFromFireStationList(filteredFireStationList);

    // We create an object including the list of persons and the list of fireStation
    // number deserving the address.
    PersonInfo personInfo = new PersonInfo(filteredPersonList, fireStationNumberList);
    List<PersonInfo> personInfoList = new ArrayList<>();
    personInfoList.add(personInfo);
    return personInfoList;
  }


  @Override
  public boolean saveAllPersons(List<Person> personList) {

    if (personList != null) {
      try {
        personRepository.saveAll(personList);
        return true;
      } catch (Exception exception) {
        logger.error("Erreur lors de l'enregistrement de la liste des personnes " + exception.getMessage()
            + " , Stack Trace : " + exception.getStackTrace());
      }
    }
    return false;
  }

}
