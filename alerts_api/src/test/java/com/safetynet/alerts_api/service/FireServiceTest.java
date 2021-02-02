package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.Fire;
import com.safetynet.alerts_api.model.FireDTO;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.fire.FireService;
import com.safetynet.alerts_api.service.fireStation.FireStationServiceImpl;
import com.safetynet.alerts_api.service.map.MapService;
import com.safetynet.alerts_api.service.personInfo.PersonInfoServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class FireServiceTest {

  @Autowired
  private FireService fireService;

  @MockBean
  private FireStationServiceImpl firestationServiceMock;

  @MockBean
  private MapService mapServiceMock;

  @MockBean
  private PersonInfoServiceImpl personInfoService;

  @MockBean
  private PersonRepository personRepositoryMock;

  @MockBean
  private MedicalRecordRepository medicalRecordRepositoryMock;

  @MockBean
  private MedicalRecord medicalRecordMock;

  @MockBean
  private Person personMock;

  /**
   * test to get a person list with the station number from an address.
   * 
   */
  @Test
  public void testGetPersonListWithStationNumber() {
    Person person = new Person();
    person.setId((long) 5);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    String address = "1 rue antonio vivaldi";
    FireStation fireStation = new FireStation();
    List<FireStation> filteredFireStationList = new ArrayList<>();
    filteredFireStationList.add(fireStation);
    List<Integer> fireStationNumberList = new ArrayList<>();
    FireDTO fireDTO = new FireDTO("Bessy", 45, "0065468", new ArrayList<>(), new ArrayList<>());
    List<FireDTO> fireDTOList = new ArrayList<>();
    fireDTOList.add(fireDTO);
    Fire fire = new Fire(fireDTOList, fireStationNumberList);

    when(personRepositoryMock.findDistinctByAddress(address)).thenReturn(
        personList);
    doNothing().when(personInfoService).setAgeAndMedicationsAndAllergiesFromPersonList(personList);
    when(firestationServiceMock.getStationNumberListFromFireStationList(
        filteredFireStationList)).thenReturn(fireStationNumberList);
    when(mapServiceMock.convertToFireDTOList(personList))
        .thenReturn(fireDTOList);

    Fire fire2 = fireService.getPersonListWithStationNumber(address);
    assertThat(fire2).isEqualTo(fire);
  }

}
