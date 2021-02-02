package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.ChildAlertDTO;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.childAlert.ChildAlertService;
import com.safetynet.alerts_api.service.map.MapService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ChildAlertServiceTest {

  @Autowired
  private ChildAlertService childAlertService;

  @MockBean
  private PersonRepository personRepositoryMock;

  @MockBean
  private MedicalRecordRepository medicalRecordRepositoryMock;

  @MockBean
  private MapService mapServiceMock;

  /**
   * test to get a children list and an adult list from an address.
   * 
   */
  @Test
  public void testGetChildrenListAndAdultListFromAddress() {
    Person person = new Person();
    person.setId((long) 5);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    String address = "1 rue antonio vivaldi";
    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2020");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);
    ChildAlertDTO childAlertDTO = new ChildAlertDTO("Bessy", "Adrien", 10);
    List<ChildAlertDTO> childAlertDTOList = new ArrayList<>();
    childAlertDTOList.add(childAlertDTO);
    List<ChildAlertDTO> childAlertDTOList2 = new ArrayList<>();
    Home home = new Home(childAlertDTOList, childAlertDTOList2);

    when(personRepositoryMock.findDistinctByAddress(address)).thenReturn(personList);
    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(mapServiceMock.convertToChildAlertDTOList(personList))
        .thenReturn(childAlertDTOList);
    when(mapServiceMock.convertToChildAlertDTOList(personList))
        .thenReturn(childAlertDTOList);

    Home home2 = childAlertService.getChildrenListAndAdultListFromAddress(address);
    assertThat(home2).isEqualTo(home);
  }

}
