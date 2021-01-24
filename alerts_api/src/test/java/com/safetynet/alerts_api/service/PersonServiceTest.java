package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.FireStationCommunity;
import com.safetynet.alerts_api.model.FireStationCommunityDTO;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.map.MapService;
import com.safetynet.alerts_api.service.person.PersonService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PersonServiceTest {

  @Autowired
  private PersonService personService;

  @MockBean
  private FireStationRepository firestationRepository;

  @MockBean
  private AddressServiceImpl addressServiceMock;

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

  /*
   * @BeforeEach private void setUp() { personService = new PersonServiceImpl(); }
   */

  /**
   * test to get a list of person from a list of fireStation number.
   * 
   */
  @Test
  public void testGetPersonListFromStationNumber() {
    // GIVEN
    FireStation fireStation1 = new FireStation();
    fireStation1.setId((long) 5);
    fireStation1.setAddress("82 Alexander Road");
    fireStation1.setStation(6);
    FireStation fireStation2 = new FireStation();
    fireStation2.setId((long) 2);
    fireStation2.setAddress("1 rue Antonio Vivaldi");
    fireStation2.setStation(2);
    List<FireStation> fireStationList = new ArrayList<>();
    fireStationList.add(fireStation1);
    fireStationList.add(fireStation2);

    List<String> addressList = new ArrayList<>();
    addressList.add("82 Alexander Road");
    addressList.add("1 rue Antonio Vivaldi");

    Person person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    Person person2 = new Person();
    person2.setEmail("unoddicab-5625@yopmail.com");
    person2.setAddress("12 rue des ecoles");
    personList.add(person2);
    Person person3 = new Person();
    person3.setEmail("unoddicab-5625@yopmail.com");
    person3.setAddress("1 rue des ecoles");
    personList.add(person3);

    when(firestationRepository.findDistinctByStation(org.mockito.ArgumentMatchers
        .anyInt())).thenReturn(fireStationList);
    when(addressServiceMock.getAddressListFromFireStationList(fireStationList)).thenReturn(addressList);
    when(personRepositoryMock.findAllByAddressInOrderByAddress(addressList)).thenReturn(personList);
    List<Person> result = personService.getPersonListFromStationNumber(1);

    // THEN
    assertThat(result).isEqualTo(personList);
    verify(personRepositoryMock,
        Mockito.times(1)).findAllByAddressInOrderByAddress(addressList);

  }

  /**
   * test to get a list of person from a list of fireStation number.
   * 
   */
  @Test
  public void testAddAddressToListFromFireStationList() {
    Person person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2020");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);
    List<Person> childrenList = new ArrayList<>();
    List<Person> adultList = new ArrayList<>();
    List<Person> childrenListTest = new ArrayList<>();
    childrenListTest.add(person);

    doNothing().when(personMock).setAge_Medications_Allergies(medicalRecord1,
        LocalDate.of(2021, 1, 11));
    personService.addPersonToListFromFireStationList(person,
        medicalRecordList, childrenList, adultList,
        LocalDate.of(2021, 1, 11));
    assertThat(childrenList).isEqualTo(childrenListTest);
  }

  /**
   * test to get a list of PersonNumberInfo from a fireStation number.
   * 
   */
  @Test
  public void testGetPersonNumberInfoListFromStationNumber() {

    FireStation fireStation1 = new FireStation();
    fireStation1.setId((long) 5);
    fireStation1.setAddress("82 Alexander Road");
    fireStation1.setStation(6);
    FireStation fireStation2 = new FireStation();
    fireStation2.setId((long) 2);
    fireStation2.setAddress("1 rue Antonio Vivaldi");
    fireStation2.setStation(2);

    List<FireStation> fireStationList = new ArrayList<>();
    fireStationList.add(fireStation1);
    fireStationList.add(fireStation2);

    List<String> addressList = new ArrayList<>();
    addressList.add("82 Alexander Road");
    addressList.add("1 rue Antonio Vivaldi");

    Person person = new Person();
    person.setId((long) 5);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);

    when(firestationRepository.findDistinctByStation(org.mockito.ArgumentMatchers
        .anyInt())).thenReturn(fireStationList);
    when(addressServiceMock.getAddressListFromFireStationList(fireStationList)).thenReturn(addressList);
    when(personRepositoryMock.findAllByAddressInOrderByAddress(addressList)).thenReturn(personList);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2020");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);

    FireStationCommunityDTO fireStationCommunityDTO = new FireStationCommunityDTO("Adrien", "Bessy", "", "", "",
        "");
    List<FireStationCommunityDTO> fireStationCommunityDTOList = new ArrayList<>();
    fireStationCommunityDTOList.add(fireStationCommunityDTO);
    when(mapServiceMock.convertToFireStationCommunityDTO(personList))
        .thenReturn(fireStationCommunityDTOList);

    FireStationCommunity fireStationCommunity = new FireStationCommunity(fireStationCommunityDTOList,
        1, 0);

    FireStationCommunity fireStationCommunity2 = personService.getPersonNumberInfoListFromStationNumber(1);
    assertThat(fireStationCommunity2).isEqualTo(fireStationCommunity);
  }

  /**
   * test to full a children list and an adult list from a list of persons.
   * 
   */
  @Test
  public void testFullChildrenListAndAdultListFromPersonList() {
    Person person = new Person();
    person.setId((long) 5);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);

    List<Person> childrenList = new ArrayList<>();
    List<Person> adultList = new ArrayList<>();

    List<Person> childrenList2 = new ArrayList<>();
    childrenList2.add(person);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2020");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);

    personService.fullChildrenListAndAdultListFromPersonList(personList, childrenList, adultList);

    assertThat(childrenList).isEqualTo(childrenList2);
  }


  /**
   * test to add persons to a list from a list of fireStation.
   * 
   */
  @Test
  public void testAddPersonToListFromFireStationList() {
    Person person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2020");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    List<Person> childrenList = new ArrayList<>();
    List<Person> adultList = new ArrayList<>();

    LocalDate currentDate = LocalDate.of(2021, 1, 11);

    List<Person> childrenList2 = new ArrayList<>();
    childrenList2.add(person);

    personService.addPersonToListFromFireStationList(person, medicalRecordList, childrenList, adultList, currentDate);
    assertThat(childrenList).isEqualTo(childrenList2);
  }

}
