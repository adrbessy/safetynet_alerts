package com.safetynet.alerts_api.service;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfo;
import com.safetynet.alerts_api.model.PersonInfoByAddress;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private static final Logger logger = LogManager.getLogger(PersonService.class);

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private FireStationRepository firestationRepository;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;


  public List<String> getAddressList(List<Integer> stationsList) {
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


  public List<Home> getChildrenListAndAdultListFromAddress(String address) {

    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);

    // we retrieve the list of children from the list of persons
    List<Person> childrenList = new ArrayList<>();
    List<Person> adultList = new ArrayList<>();
    filteredPersonList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
            if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
              personIterator.setAge_Mediations_Allergies(medicalRecordIterator);
              if (personIterator.getAge() <= 18) {
                childrenList.add(personIterator);
              } else {
                adultList.add(personIterator);
              }
            }
          });
    });

    // We create an object including the list of children and the list of adults
    Home home = new Home(childrenList, adultList);
    if (childrenList.isEmpty()) {
      return new ArrayList<>();
    }
    List<Home> homeList = new ArrayList<>();
    homeList.add(home);

    return homeList;
  }

  private List<String> getEmailListFromPersonList(List<Person> personList) {
    List<String> emailList = new ArrayList<>();
    if (personList != null) {
      personList.forEach(personIterator -> {
        if (personIterator.getEmail() != null && !personIterator.getEmail().isEmpty()) {
          emailList.add(personIterator.getEmail());
        }
      });
    }
    return emailList;
  }

  public List<String> getPersonEmailFromCity(String city) {
    List<Person> filteredPersonList = personRepository.findDistinctByCityAllIgnoreCase(city);
    List<String> emailList = getEmailListFromPersonList(filteredPersonList);
    List<String> emailListNoDuplicates = emailList.stream().distinct().collect(Collectors.toList());
    return emailListNoDuplicates;
  }


  public List<PersonInfoByAddress> getPersonInfoByAddressList(List<Integer> stationsList) {
    // We create an object including the list of persons and the list of fireStation
    // number deserving the address.

    List<PersonInfoByAddress> personInfoByAddressList = new ArrayList<>();
    List<String> addressList = getAddressList(stationsList);
    if (addressList != null) {
      addressList.forEach(addressIterator -> {
        PersonInfoByAddress personInfoByAddress = new PersonInfoByAddress(addressIterator,
            getPersonInfoListByAddress(addressIterator));
        personInfoByAddressList.add(personInfoByAddress);
      });
    }
    return personInfoByAddressList;
  }


  public List<Person> getPersonInfoByFirstNameAndLastName(String firstName, String lastName) {
    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    System.out.println("filteredPersonList:" + filteredPersonList);
    filteredPersonList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
            if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
              personIterator.setAge_Mediations_Allergies(medicalRecordIterator);
            }
          });
    });
    return filteredPersonList;
  }


  public List<Person> getPersonInfoByLastName(String lastName) {
    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findByLastNameAllIgnoreCase(lastName);
    filteredPersonList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
            if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
              personIterator.setAge_Mediations_Allergies(medicalRecordIterator);
            }
          });
    });

    return filteredPersonList;
  }


  public List<Person> getPersonInfoByFirstNameAndLastNameThenOnlyLastName(String firstName, String lastName) {
    List<Person> personInfoByFirstNameAndLastName = getPersonInfoByFirstNameAndLastName(firstName, lastName);
    List<Person> personInfoByLastName = getPersonInfoByLastName(lastName);
    personInfoByLastName.forEach(personIterator -> {
      if (!personInfoByFirstNameAndLastName.contains(personIterator))
        personInfoByFirstNameAndLastName.add(personIterator);
    });

    return personInfoByFirstNameAndLastName;

  }


  public List<Person> getPersonInfoListByAddress(String address) {

    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);

    filteredPersonList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
            if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
              personIterator.setAge_Mediations_Allergies(medicalRecordIterator);
            }
          });
    });

    return filteredPersonList;

  }

  public List<PersonInfo> getPersonListWithStationNumber(String address) {

    List<Person> filteredPersonList = getPersonInfoListByAddress(address);

    List<FireStation> filteredFireStationList = firestationRepository.findDistinctByAddress(address);

    System.out.println("filteredFireStationList: " + filteredFireStationList);

    List<Integer> fireStationNumberList = getStationNumberListFromFireStationList(filteredFireStationList);

    System.out.println("fireStationNumberList: " + fireStationNumberList);

    // We create an object including the list of persons and the list of fireStation
    // number deserving the address.
    PersonInfo personInfo = new PersonInfo(filteredPersonList, fireStationNumberList);
    List<PersonInfo> personInfoList = new ArrayList<>();
    personInfoList.add(personInfo);

    return personInfoList;
  }

  private List<Integer> getStationNumberListFromFireStationList(List<FireStation> fireStationList) {
    List<Integer> fireStationNumberList = new ArrayList<>();
    if (fireStationList != null) {
      fireStationList.forEach(fireStationIterator -> {
        if (fireStationIterator.getStation() != null) {
          fireStationNumberList.add(fireStationIterator.getStation());
        }
      });
    }
    return fireStationNumberList;
  }


  /**
   * Sauvegarde la liste des personnes passées en paramètre
   *
   * @param personList Liste des personnes à sauvegarder
   */
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
