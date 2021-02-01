package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

    when(personRepositoryMock.findDistinctByAddress(address)).thenReturn(
        personList);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2019");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase(
        "Adrien", "Bessy")).thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate()).thenReturn("16/06/2019");
    doNothing().when(personMock).setAge(medicalRecord1,
        LocalDate.of(2021, 1, 11));
    doNothing().when(personMock).setMedicationsAndAllergies(medicalRecord1);

    FireStation fireStation = new FireStation();
    List<FireStation> filteredFireStationList = new ArrayList<>();
    filteredFireStationList.add(fireStation);

    List<Integer> fireStationNumberList = new ArrayList<>();

    when(firestationServiceMock.getStationNumberListFromFireStationList(
        filteredFireStationList)).thenReturn(fireStationNumberList);

    FireDTO fireDTO = new FireDTO("Bessy", 45, "0065468", new ArrayList<>(), new ArrayList<>());
    List<FireDTO> fireDTOList = new ArrayList<>();
    fireDTOList.add(fireDTO);

    when(mapServiceMock.convertToFireDTOList(personList))
        .thenReturn(fireDTOList);

    Fire fire = new Fire(fireDTOList, fireStationNumberList);

    Fire fire2 = fireService.getPersonListWithStationNumber(address);

    assertThat(fire2).isEqualTo(fire);

  }

  /**
   * test to get a person list by address.
   * 
   */
  @Test
  public void testGetPersonListByAddress() {
    Person person = new Person();
    person.setId((long) 5);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    String address = "1 rue antonio vivaldi";

    when(personRepositoryMock.findDistinctByAddress(address)).thenReturn(personList);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2019");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate())
        .thenReturn("16/06/2019");
    doNothing().when(personMock).setAge(medicalRecord1,
        LocalDate.of(2021, 1, 11));
    doNothing().when(personMock).setMedicationsAndAllergies(medicalRecord1);

    List<Person> personList2 = fireService.getPersonListByAddress(address);
    verify(personRepositoryMock,
        Mockito.times(1)).findDistinctByAddress(address);
    assertThat(personList2).isNotEqualTo(null);
  }

  /**
   * test to set age, medications and allergies from a person list.
   * 
   */
  @Test
  public void testSetAgeAndMedicationsAndAllergiesFromPersonList() {
    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2019");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate())
        .thenReturn("16/06/2019");
    doNothing().when(personMock).setAge(medicalRecord1,
        LocalDate.of(2021, 1, 11));
    doNothing().when(personMock).setMedicationsAndAllergies(medicalRecord1);
    Person person = new Person();
    person.setId((long) 5);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);

    fireService.setAgeAndMedicationsAndAllergiesFromPersonList(personList);
    assertThat(person.getAge()).isEqualTo(1);
  }

}
