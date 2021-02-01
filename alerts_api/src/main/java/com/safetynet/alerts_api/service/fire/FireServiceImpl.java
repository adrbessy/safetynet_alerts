package com.safetynet.alerts_api.service.fire;

import com.safetynet.alerts_api.model.Fire;
import com.safetynet.alerts_api.model.FireDTO;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import com.safetynet.alerts_api.service.map.MapService;
import java.time.LocalDate;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireServiceImpl implements FireService {

  private static final Logger logger = LogManager.getLogger(FireServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private FireStationRepository firestationRepository;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  @Autowired
  private FireStationService firestationService;

  @Autowired
  private MapService mapService;

  @Override
  public Fire getPersonListWithStationNumber(String address) {
    Fire fire = null;
    try {
      List<Person> filteredPersonList = getPersonListByAddress(address);
      List<FireStation> filteredFireStationList = firestationRepository.findDistinctByAddress(address);
      List<Integer> fireStationNumberList = firestationService
          .getStationNumberListFromFireStationList(filteredFireStationList);
      List<FireDTO> fireDTOList = mapService.convertToFireDTOList(filteredPersonList);
      // We create an object including the list of persons and the list of fireStation
      // number deserving the address.
      fire = new Fire(fireDTOList, fireStationNumberList);
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList with a station number :"
          + exception.getMessage());
    }
    return fire;
  }


  @Override
  public List<Person> getPersonListByAddress(String address) {
    List<Person> filteredPersonList = null;
    try {
      // we retrieve the list of persons corresponding to the address
      filteredPersonList = personRepository.findDistinctByAddress(address);
      setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList);
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList By address :"
          + exception.getMessage());
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
