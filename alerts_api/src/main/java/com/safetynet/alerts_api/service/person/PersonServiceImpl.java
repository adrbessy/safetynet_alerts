package com.safetynet.alerts_api.service.person;

import com.safetynet.alerts_api.model.ChildAlertDTO;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfo;
import com.safetynet.alerts_api.model.PersonInfo2DTO;
import com.safetynet.alerts_api.model.PersonInfoByAddress;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import com.safetynet.alerts_api.model.PersonNumberInfo;
import com.safetynet.alerts_api.model.PersonNumberInfoDTO;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.fireStation.FireStationServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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


  @Override
  public void deletePerson(String firstName, String lastName) {
    try {
      personRepository.deletePersonByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    } catch (Exception exception) {
      logger.error("Error when we try to delete a person :" + exception.getMessage());
    }
  }

  @Override
  public Person getPerson(final Long id) {
    Optional<Person> pers = personRepository.findById(id);
    if (pers.isPresent()) {
      Person personToUpdate = pers.get();
      return personToUpdate;
    } else {
      return null;
    }
  }

  @Override
  public Person savePerson(Person person) {
    Person savedPerson = personRepository.save(person);
    return savedPerson;
  }


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
  public PersonNumberInfo getPersonNumberInfoListFromStationNumber(Integer stationNumber) {
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
        List<PersonNumberInfoDTO> PersonNumberInfoDTOList = new ArrayList<>();
        filteredPersonList.forEach(personIterator -> {
          PersonNumberInfoDTO personNumberInfoDTO = new PersonNumberInfoDTO(personIterator.getFirstName(),
              personIterator.getLastName(),
              personIterator.getAddress(),
              personIterator.getCity(),
              personIterator.getZip(), personIterator.getPhone());
          PersonNumberInfoDTOList.add(personNumberInfoDTO);
        });
        PersonNumberInfo personNumberInfo = new PersonNumberInfo(PersonNumberInfoDTOList,
            child, adult);
        // List<PersonNumberInfo> personNumberInfoList = new ArrayList<>();
        // personNumberInfoList.add(personNumberInfo);

        return personNumberInfo;

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
    personList.forEach(personIterator -> {
      addPersonToListFromFireStationList(personIterator,
          medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
              personIterator.getFirstName(), personIterator.getLastName()),
          childrenList,
          adultList, LocalDate.now());
    });
  }


  @Override
  public void addPersonToListFromFireStationList(Person personIterator, List<MedicalRecord> medicalRecordList,
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
  public void setAgeAndMedicationsAndAllergiesFromPersonList(List<Person> personList) {
    personList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
            if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
              personIterator.setAge_Medications_Allergies(medicalRecordIterator, LocalDate.now());
            }
          });
    });
  }


  @Override
  public Home getChildrenListAndAdultListFromAddress(String address) {

    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);

    // we retrieve the list of children from the list of persons
    List<Person> childrenList = new ArrayList<>();
    List<Person> adultList = new ArrayList<>();
    fullChildrenListAndAdultListFromPersonList(filteredPersonList, childrenList, adultList);

    List<ChildAlertDTO> childrenDTOList = new ArrayList<>();
    childrenList.forEach(personIterator -> {
      ChildAlertDTO childAlertDTO = new ChildAlertDTO(personIterator.getLastName(), personIterator.getFirstName(),
          personIterator.getAge());
      childrenDTOList.add(childAlertDTO);
    });

    List<ChildAlertDTO> adultDTOList = new ArrayList<>();
    adultList.forEach(personIterator -> {
      ChildAlertDTO childAlertDTO = new ChildAlertDTO(personIterator.getLastName(), personIterator.getFirstName(),
          personIterator.getAge());
      adultDTOList.add(childAlertDTO);
    });

    // We create an object including the list of children and the list of adults
    Home home = new Home(childrenDTOList, adultDTOList);
    if (childrenList.isEmpty()) {
      return new Home(new ArrayList<>(), new ArrayList<>());
    }
    return home;
  }


  @Override
  public List<PersonInfoByAddress> getPersonInfoByAddressList(List<Integer> stationsList) {
    // We create an object including the list of persons and the list of fireStation
    // number deserving the address.
    List<String> addressList = addressService.getAddressListFromStationNumberList(stationsList);
    List<PersonInfoByAddress> personInfoByAddressList = new ArrayList<>();
    if (addressList != null) {
      addressList.forEach(addressIterator -> {
        List<Person> personList = getPersonListByAddress(addressIterator);
        List<PersonInfoDTO> PersonInfoDTOList = new ArrayList<>();
        personList.forEach(personIterator -> {
          PersonInfoDTO personInfoDTO = new PersonInfoDTO(personIterator.getLastName(), personIterator.getAge(),
              personIterator.getPhone(), personIterator.getMedications(), personIterator.getAllergies());
          PersonInfoDTOList.add(personInfoDTO);
        });
        PersonInfoByAddress personInfoByAddress = new PersonInfoByAddress(addressIterator,
            PersonInfoDTOList);
        personInfoByAddressList.add(personInfoByAddress);
      });
    }
    return personInfoByAddressList;
  }


  @Override
  public List<Person> getPersonListByFirstNameAndLastName(String firstName, String lastName) {
    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList);
    return filteredPersonList;
  }


  @Override
  public List<Person> getPersonListByLastName(String lastName) {
    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findByLastNameAllIgnoreCase(lastName);
    setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList);
    return filteredPersonList;
  }


  @Override
  public List<PersonInfo2DTO> getPersonListByFirstNameAndLastNameThenOnlyLastName(String firstName, String lastName) {
    List<Person> personInfoByFirstNameAndLastName = getPersonListByFirstNameAndLastName(firstName, lastName);
    List<Person> personInfoByLastName = getPersonListByLastName(lastName);
    personInfoByLastName.forEach(personIterator -> {
      if (!personInfoByFirstNameAndLastName.contains(personIterator))
        personInfoByFirstNameAndLastName.add(personIterator);
    });
    List<PersonInfo2DTO> PersonInfo2DTOList = new ArrayList<>();
    personInfoByFirstNameAndLastName.forEach(personIterator -> {
      PersonInfo2DTO personInfo2DTO = new PersonInfo2DTO(personIterator.getLastName(), personIterator.getAge(),
          personIterator.getAddress(), personIterator.getCity(), personIterator.getZip(), personIterator.getEmail(),
          personIterator.getMedications(), personIterator.getAllergies());
      PersonInfo2DTOList.add(personInfo2DTO);
    });
    return PersonInfo2DTOList;
  }


  @Override
  public List<Person> getPersonListByAddress(String address) {
    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);
    setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList);
    return filteredPersonList;
  }


  @Override
  public PersonInfo getPersonListWithStationNumber(String address) {

    List<Person> filteredPersonList = getPersonListByAddress(address);

    List<FireStation> filteredFireStationList = firestationRepository.findDistinctByAddress(address);

    List<Integer> fireStationNumberList = firestationService
        .getStationNumberListFromFireStationList(filteredFireStationList);

    List<PersonInfoDTO> PersonInfoDTOList = new ArrayList<>();
    filteredPersonList.forEach(personIterator -> {
      PersonInfoDTO personInfoDTO = new PersonInfoDTO(personIterator.getLastName(), personIterator.getAge(),
          personIterator.getPhone(), personIterator.getMedications(), personIterator.getAllergies());
      PersonInfoDTOList.add(personInfoDTO);
    });

    // We create an object including the list of persons and the list of fireStation
    // number deserving the address.
    PersonInfo personInfo = new PersonInfo(PersonInfoDTOList, fireStationNumberList);
    // List<PersonInfo> personInfoList = new ArrayList<>();
    // personInfoList.add(personInfo);
    return personInfo;
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
