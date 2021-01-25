package com.safetynet.alerts_api.service.map;

import com.safetynet.alerts_api.model.ChildAlertDTO;
import com.safetynet.alerts_api.model.FireDTO;
import com.safetynet.alerts_api.model.FireStationCommunityDTO;
import com.safetynet.alerts_api.model.Person;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MapService {

  public List<FireStationCommunityDTO> convertToFireStationCommunityDTOList(List<Person> personList) {
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

  public List<ChildAlertDTO> convertToChildAlertDTOList(List<Person> personList) {
    List<ChildAlertDTO> childAlertDTOList = new ArrayList<>();
    personList.forEach(personIterator -> {
      ChildAlertDTO childAlertDTO = new ChildAlertDTO(personIterator.getLastName(),
          personIterator.getFirstName(),
          personIterator.getAge());
      childAlertDTOList.add(childAlertDTO);
    });
    return childAlertDTOList;
  }

  public List<FireDTO> convertToFireDTOList(List<Person> personList) {
    List<FireDTO> fireDTOList = new ArrayList<>();
    personList.forEach(personIterator -> {
      FireDTO fireDTO = new FireDTO(personIterator.getLastName(), personIterator.getAge(),
          personIterator.getPhone(), personIterator.getMedications(), personIterator.getAllergies());
      fireDTOList.add(fireDTO);
    });
    return fireDTOList;
  }

}
