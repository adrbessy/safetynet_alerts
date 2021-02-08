package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.fireStation.FireStationServiceImpl;
import com.safetynet.alerts_api.service.map.MapService;
import com.safetynet.alerts_api.service.personInfo.PersonInfoService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PersonInfoServiceTest {

  @Autowired
  private PersonInfoService personInfoService;

  @MockBean
  private FireStationRepository firestationRepository;

  @MockBean
  private AddressServiceImpl addressServiceMock;

  @MockBean
  private FireStationServiceImpl firestationServiceMock;

  @MockBean
  private MapService mapServiceMock;

  @MockBean
  private PersonRepository personRepositoryMock;

  @MockBean
  private MedicalRecordRepository medicalRecordRepositoryMock;

  @MockBean
  private Person personMock;

  @MockBean
  private MedicalRecord medicalRecordMock;

  private MedicalRecord medicalRecord1;
  private List<MedicalRecord> medicalRecordList;
  private Person person;
  private List<Person> personList;

  @BeforeEach
  private void setUp() {
    medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2019");
    medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);
    person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    personList = new ArrayList<>();
    personList.add(person);
  }

  /**
   * test to set age, medications and allergies from a person list.
   * 
   */
  @Test
  public void testSetAgeAndMedicationsAndAllergiesFromPersonList() {
    person.setFirstName("Adrien");
    person.setLastName("Bessy");

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate())
        .thenReturn("16/06/2019");

    personInfoService.setAgeAndMedicationsAndAllergiesFromPersonList(personList, LocalDate.of(2021, 2, 8));
    assertThat(person.getAge()).isEqualTo(1);
  }


  /**
   * test to get a person list from the first name and the last name.
   * 
   */
  @Test
  public void testGetPersonListByFirstNameAndLastName() {
    when(personRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy")).thenReturn(personList);
    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate())
        .thenReturn("16/06/2019");
    doNothing().when(personMock).setAge(medicalRecord1,
        LocalDate.of(2021, 1, 11));
    doNothing().when(personMock).setMedicationsAndAllergies(medicalRecord1);

    List<Person> personList2 = personInfoService.getPersonListByFirstNameAndLastName("Adrien", "Bessy");
    assertThat(personList2).isNotEqualTo(null);
  }


  /**
   * test to get a person list from the last name.
   * 
   */
  @Test
  public void testGetPersonListByLastName() {
    when(personRepositoryMock.findByLastNameAllIgnoreCase("Bessy")).thenReturn(personList);
    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate())
        .thenReturn("16/06/2019");
    doNothing().when(personMock).setAge(medicalRecord1,
        LocalDate.of(2021, 1, 11));
    doNothing().when(personMock).setMedicationsAndAllergies(medicalRecord1);

    List<Person> personList2 = personInfoService.getPersonListByLastName("Bessy");
    assertThat(personList2).isNotEqualTo(null);
  }


  /**
   * test to get a person list from the first name and the last name, then only
   * from the last name.
   * 
   */
  @Test
  public void testGetPersonListByFirstNameAndLastNameThenOnlyLastName() {
    PersonInfoDTO personInfoDTO = new PersonInfoDTO("Bessy", 45,
        "1 rue antonio vivaldi", "Paris", "75000", "dgsdfgf@gmail.com",
        new ArrayList<>(), new ArrayList<>());
    List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();
    personInfoDTOList.add(personInfoDTO);

    when(personRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy")).thenReturn(personList);
    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate())
        .thenReturn("16/06/2019");
    doNothing().when(personMock).setAge(medicalRecord1,
        LocalDate.of(2021, 1, 11));
    doNothing().when(personMock).setMedicationsAndAllergies(medicalRecord1);
    when(personRepositoryMock.findByLastNameAllIgnoreCase("Bessy")).thenReturn(personList);
    when(mapServiceMock.convertToPersonInfoDTOList(personList))
        .thenReturn(personInfoDTOList);

    List<PersonInfoDTO> personInfoDTOList2 = personInfoService.getPersonListByFirstNameAndLastNameThenOnlyLastName(
        "Adrien",
        "Bessy");
    assertThat(personInfoDTOList2).isEqualTo(personInfoDTOList);

  }

}
