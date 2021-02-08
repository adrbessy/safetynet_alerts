package com.safetynet.alerts_api.service.map;

import com.safetynet.alerts_api.model.ChildAlertDTO;
import com.safetynet.alerts_api.model.FireDTO;
import com.safetynet.alerts_api.model.FireStationCommunityDTO;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MapService {

  private static final Logger logger = LogManager.getLogger(MapService.class);


  /**
   * Get a list of persons with the following attributes: FirstName, LastName,
   * Address, City, Zip, Phone
   * 
   * @param personList A list of persons
   * @return A List of Person
   */
  public List<FireStationCommunityDTO> convertToFireStationCommunityDTOList(List<Person> personList) {
    logger.debug("in the method convertToFireStationCommunityDTOList in the class MapService");
    List<FireStationCommunityDTO> fireStationCommunityDTOList = new ArrayList<>();
    personList.forEach(personIterator -> {
      FireStationCommunityDTO fireStationCommunityDTO = new FireStationCommunityDTO(personIterator.getFirstName(),
          personIterator.getLastName(),
          personIterator.getAddress(),
          personIterator.getCity(),
          personIterator.getZip(), personIterator.getPhone());
      fireStationCommunityDTOList.add(fireStationCommunityDTO);
    });
    return fireStationCommunityDTOList;
  }

  /**
   * Get a list of persons with the following attributes: FirstName, LastName, Age
   * 
   * @param personList A list of persons
   * @return A List of Person
   */
  public List<ChildAlertDTO> convertToChildAlertDTOList(List<Person> personList) {
    logger.debug("in the method convertToChildAlertDTOList in the class MapService");
    List<ChildAlertDTO> childAlertDTOList = new ArrayList<>();
    personList.forEach(personIterator -> {
      ChildAlertDTO childAlertDTO = new ChildAlertDTO(personIterator.getLastName(),
          personIterator.getFirstName(),
          personIterator.getAge());
      childAlertDTOList.add(childAlertDTO);
    });
    return childAlertDTOList;
  }

  /**
   * Get a list of persons with the following attributes: LastName, Age, Phone,
   * Medications, Allergies
   * 
   * @param personList A list of persons
   * @return A List of Person
   */
  public List<FireDTO> convertToFireDTOList(List<Person> personList) {
    logger.debug("in the method convertToFireDTOList in the class MapService");
    List<FireDTO> fireDTOList = new ArrayList<>();
    personList.forEach(personIterator -> {
      FireDTO fireDTO = new FireDTO(personIterator.getLastName(), personIterator.getAge(),
          personIterator.getPhone(), personIterator.getMedications(), personIterator.getAllergies());
      fireDTOList.add(fireDTO);
    });
    return fireDTOList;
  }

  /**
   * Get a list of persons with the following attributes: LastName, Age, Address,
   * City, Zip, Email, Medications, Allergies
   * 
   * @param personList A list of persons
   * @return A List of Person
   */
  public List<PersonInfoDTO> convertToPersonInfoDTOList(List<Person> personList) {
    logger.debug("in the method convertToPersonInfoDTOList in the class MapService");
    List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();
    personList.forEach(personIterator -> {
      PersonInfoDTO personInfoDTO = new PersonInfoDTO(personIterator.getLastName(), personIterator.getAge(),
          personIterator.getAddress(), personIterator.getCity(), personIterator.getZip(), personIterator.getEmail(),
          personIterator.getMedications(), personIterator.getAllergies());
      personInfoDTOList.add(personInfoDTO);
    });
    return personInfoDTOList;
  }

}
