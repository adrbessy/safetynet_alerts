package com.safetynet.alerts_api.service.personInfo;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.map.MapService;
import java.time.LocalDate;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

  private static final Logger logger = LogManager.getLogger(PersonInfoServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  @Autowired
  private MapService mapService;


  @Override
  public List<PersonInfoDTO> getPersonListByFirstNameAndLastNameThenOnlyLastName(String firstName, String lastName) {
    List<PersonInfoDTO> PersonInfoDTOList = null;
    try {
      List<Person> personInfoByFirstNameAndLastName = getPersonListByFirstNameAndLastName(firstName, lastName);
      List<Person> personInfoByLastName = getPersonListByLastName(lastName);
      personInfoByLastName.forEach(personIterator -> {
        if (!personInfoByFirstNameAndLastName.contains(personIterator))
          personInfoByFirstNameAndLastName.add(personIterator);
      });

      PersonInfoDTOList = mapService.convertToPersonInfoDTOList(personInfoByFirstNameAndLastName);
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList By FirstName And LastName Then Only LastName :"
          + exception.getMessage());
    }
    return PersonInfoDTOList;
  }


  @Override
  public List<Person> getPersonListByFirstNameAndLastName(String firstName, String lastName) {
    List<Person> filteredPersonList = null;
    try {
      // we retrieve the list of persons corresponding to the address
      filteredPersonList = personRepository.findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
      setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList);
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList By FirstName And LastName :" + exception.getMessage());
    }
    return filteredPersonList;
  }


  @Override
  public List<Person> getPersonListByLastName(String lastName) {
    List<Person> filteredPersonList = null;
    try {
      // we retrieve the list of persons corresponding to the address
      filteredPersonList = personRepository.findByLastNameAllIgnoreCase(lastName);
      setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList);
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList By LastName :" + exception.getMessage());
    }
    return filteredPersonList;
  }


  @Override
  public void setAgeAndMedicationsAndAllergiesFromPersonList(List<Person> personList) {
    try {
      personList.forEach(personIterator -> {
        medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
            personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
              if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
                personIterator.setAge(medicalRecordIterator, LocalDate.now());
                personIterator.setMedicationsAndAllergies(medicalRecordIterator);
              }
            });
      });
    } catch (Exception exception) {
      logger.error("Error when we try to set age, medications and allergies :" + exception.getMessage());
    }
  }


}
