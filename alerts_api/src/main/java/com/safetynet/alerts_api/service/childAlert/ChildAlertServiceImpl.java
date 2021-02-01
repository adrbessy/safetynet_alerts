package com.safetynet.alerts_api.service.childAlert;

import com.safetynet.alerts_api.model.ChildAlertDTO;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.map.MapService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildAlertServiceImpl implements ChildAlertService {

  private static final Logger logger = LogManager.getLogger(ChildAlertServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  @Autowired
  private MapService mapService;

  @Override
  public Home getChildrenListAndAdultListFromAddress(String address) {
    Home home = null;
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
      home = new Home(childrenDTOList, adultDTOList);
      if (childrenList.isEmpty()) {
        return new Home(new ArrayList<>(), new ArrayList<>());
      }
    } catch (Exception exception) {
      logger.error("Error when we try to get ChildrenList And AdultList From an address :" + exception.getMessage());
    }
    return home;
  }


  @Override
  public void fullChildrenListAndAdultListFromPersonList(List<Person> personList, List<Person> childrenList,
      List<Person> adultList) {
    try {
      personList.forEach(personIterator -> {
        medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
            personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
              if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
                personIterator.setAge(medicalRecordIterator, LocalDate.now());
                if (personIterator.getAge() <= 18) {
                  childrenList.add(personIterator);
                } else {
                  adultList.add(personIterator);
                }
              }
            });
      });
    } catch (Exception exception) {
      logger.error("Error when we try to full children list and adult list :" + exception.getMessage());
    }
  }

}
