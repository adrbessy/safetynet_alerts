package com.safetynet.alerts_api.service.childrenAdults;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildrenAdultsServiceImpl implements ChildrenAdultsService {

  private static final Logger logger = LogManager.getLogger(ChildrenAdultsServiceImpl.class);

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  /**
   * full a list of children and a list of adults from a given Person List.
   * 
   * @param personList   A Person List
   * @param childrenList A Person List (probably empty) to full of children
   * @param adultList    A Person List (probably empty) to full of adults
   * @return The three lists included in a Map object
   */
  @Override
  public Map<String, List<Person>> fullChildrenListAndAdultListFromPersonList(List<Person> personList,
      List<Person> childrenList,
      List<Person> adultList) {
    logger.debug("in the method fullChildrenListAndAdultListFromPersonList in the class ChildrenAdultsServiceImpl");
    try {
      personList.forEach(personIterator -> {
        medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
            personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
              if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
                personIterator.setAge(medicalRecordIterator.getBirthdate(), LocalDate.now());
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
    Map<String, List<Person>> map = new HashMap<String, List<Person>>();
    map.put("childrenList", childrenList);
    map.put("adultList", adultList);
    return map;
  }

}
