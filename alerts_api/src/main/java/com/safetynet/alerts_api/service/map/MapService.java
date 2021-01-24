package com.safetynet.alerts_api.service.map;

import com.safetynet.alerts_api.model.FireStationCommunityDTO;
import com.safetynet.alerts_api.model.Person;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MapService {

  public List<FireStationCommunityDTO> convertToFireStationCommunityDTO(List<Person> personList) {
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

}
