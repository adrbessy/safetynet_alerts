package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.ChildAlertDTO;
import com.safetynet.alerts_api.model.Fire;
import com.safetynet.alerts_api.model.FireDTO;
import com.safetynet.alerts_api.model.FireDTOByAddress;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.FireStationCommunity;
import com.safetynet.alerts_api.model.FireStationCommunityDTO;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.community.CommunityService;
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
public class CommunityServiceTest {

  @Autowired
  private CommunityService communityService;

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
    List<Person> result = communityService.getPersonListFromStationNumber(1);

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
    communityService.addPersonToListFromFireStationList(person,
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
    when(mapServiceMock.convertToFireStationCommunityDTOList(personList))
        .thenReturn(fireStationCommunityDTOList);

    FireStationCommunity fireStationCommunity = new FireStationCommunity(fireStationCommunityDTOList,
        1, 0);

    FireStationCommunity fireStationCommunity2 = communityService.getPersonNumberInfoListFromStationNumber(1);
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

    communityService.fullChildrenListAndAdultListFromPersonList(personList, childrenList, adultList);

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

    communityService.addPersonToListFromFireStationList(person, medicalRecordList, childrenList, adultList,
        currentDate);
    assertThat(childrenList).isEqualTo(childrenList2);
  }

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

    when(personRepositoryMock.findDistinctByAddress(address)).thenReturn(personList);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2020");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);

    ChildAlertDTO childAlertDTO = new ChildAlertDTO("Bessy", "Adrien", 10);
    List<ChildAlertDTO> childAlertDTOList = new ArrayList<>();
    childAlertDTOList.add(childAlertDTO);
    List<ChildAlertDTO> childAlertDTOList2 = new ArrayList<>();

    when(mapServiceMock.convertToChildAlertDTOList(personList))
        .thenReturn(childAlertDTOList);
    when(mapServiceMock.convertToChildAlertDTOList(personList))
        .thenReturn(childAlertDTOList);

    Home home = new Home(childAlertDTOList, childAlertDTOList2);

    Home home2 = communityService.getChildrenListAndAdultListFromAddress(address);
    assertThat(home2).isEqualTo(home);
  }


  /**
   * test to get a children list and an adult list from an address if children
   * List is empty.
   * 
   */
  @Test
  public void testGetChildrenListAndAdultListFromAddressIfChildrenListIsEmpty() {
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
    medicalRecord1.setBirthdate("16/06/1988");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);

    List<ChildAlertDTO> childAlertDTOList = new ArrayList<>();

    when(mapServiceMock.convertToChildAlertDTOList(personList))
        .thenReturn(childAlertDTOList);
    when(mapServiceMock.convertToChildAlertDTOList(personList))
        .thenReturn(childAlertDTOList);

    Home home = new Home(new ArrayList<>(), new ArrayList<>());

    Home home2 = communityService.getChildrenListAndAdultListFromAddress(address);
    assertThat(home2).isEqualTo(home);
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
    doNothing().when(personMock).setAge_Medications_Allergies(medicalRecord1,
        LocalDate.of(2021, 1, 11));

    Person person = new Person();
    person.setId((long) 5);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);

    communityService.setAgeAndMedicationsAndAllergiesFromPersonList(personList);
    assertThat(person.getAge()).isEqualTo(1);
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
    doNothing().when(personMock).setAge_Medications_Allergies(medicalRecord1,
        LocalDate.of(2021, 1, 11));

    List<Person> personList2 = communityService.getPersonListByAddress(address);
    verify(personRepositoryMock,
        Mockito.times(1)).findDistinctByAddress(address);
    assertThat(personList2).isNotEqualTo(null);
  }


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
    doNothing().when(personMock).setAge_Medications_Allergies(medicalRecord1,
        LocalDate.of(2021, 1, 11));

    FireStation fireStation = new FireStation();
    List<FireStation> filteredFireStationList = new ArrayList<>();
    filteredFireStationList.add(fireStation);

    List<Integer> fireStationNumberList = new ArrayList<>();
    /*
     * when(firestationRepositoryMock.findDistinctByAddress(address)).thenReturn(
     * filteredFireStationList);
     */
    when(firestationServiceMock.getStationNumberListFromFireStationList(
        filteredFireStationList)).thenReturn(fireStationNumberList);

    FireDTO fireDTO = new FireDTO("Bessy", 45, "0065468", new ArrayList<>(), new ArrayList<>());
    List<FireDTO> fireDTOList = new ArrayList<>();
    fireDTOList.add(fireDTO);

    when(mapServiceMock.convertToFireDTOList(personList))
        .thenReturn(fireDTOList);

    Fire fire = new Fire(fireDTOList, fireStationNumberList);

    Fire fire2 = communityService.getPersonListWithStationNumber(address);

    assertThat(fire2).isEqualTo(fire);

  }


  /**
   * test to get a person list from the first name and the last name.
   * 
   */
  @Test
  public void testGetPersonListByFirstNameAndLastName() {
    Person person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    when(personRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy")).thenReturn(personList);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2019");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate())
        .thenReturn("16/06/2019");
    doNothing().when(personMock).setAge_Medications_Allergies(medicalRecord1,
        LocalDate.of(2021, 1, 11));

    List<Person> personList2 = communityService.getPersonListByFirstNameAndLastName("Adrien", "Bessy");
    assertThat(personList2).isNotEqualTo(null);
  }


  /**
   * test to get a person list from the first name and the last name, then only
   * from the last name.
   * 
   */
  @Test
  public void testGetPersonListByLastName() {
    Person person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    when(personRepositoryMock.findByLastNameAllIgnoreCase("Bessy")).thenReturn(personList);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2019");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate())
        .thenReturn("16/06/2019");
    doNothing().when(personMock).setAge_Medications_Allergies(medicalRecord1,
        LocalDate.of(2021, 1, 11));

    List<Person> personList2 = communityService.getPersonListByLastName("Bessy");
    assertThat(personList2).isNotEqualTo(null);
  }


  /**
   * test to get a person list from the first name and the last name, then only
   * from the last name.
   * 
   */
  @Test
  public void testGetPersonListByFirstNameAndLastNameThenOnlyLastName() {
    Person person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    when(personRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy")).thenReturn(personList);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2019");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy"))
        .thenReturn(medicalRecordList);
    when(medicalRecordMock.getBirthdate())
        .thenReturn("16/06/2019");
    doNothing().when(personMock).setAge_Medications_Allergies(medicalRecord1,
        LocalDate.of(2021, 1, 11));

    when(personRepositoryMock.findByLastNameAllIgnoreCase("Bessy")).thenReturn(personList);

    PersonInfoDTO personInfoDTO = new PersonInfoDTO("Bessy", 45,
        "1 rue antonio vivaldi", "Paris", "75000", "dgsdfgf@gmail.com",
        new ArrayList<>(), new ArrayList<>());
    List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();
    personInfoDTOList.add(personInfoDTO);
    when(mapServiceMock.convertToPersonInfoDTOList(personList))
        .thenReturn(personInfoDTOList);

    List<PersonInfoDTO> personInfoDTOList2 = communityService.getPersonListByFirstNameAndLastNameThenOnlyLastName(
        "Adrien",
        "Bessy");

    assertThat(personInfoDTOList2).isEqualTo(personInfoDTOList);

  }


  /**
   * test to get a PersonInfo by address from a station number List.
   * 
   */
  @Test
  public void testGetPersonInfoByAddressList() {
    List<Integer> stationNumberList = new ArrayList<>();
    stationNumberList.add(4);
    stationNumberList.add(5);
    List<String> addressList = new ArrayList<>();
    addressList.add("1 rue antonio vivaldi");
    when(addressServiceMock.getAddressListFromStationNumberList(stationNumberList)).thenReturn(addressList);

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
    doNothing().when(personMock).setAge_Medications_Allergies(medicalRecord1,
        LocalDate.of(2021, 1, 11));

    FireDTO fireDTO = new FireDTO("Bessy", 45, "0065468", new ArrayList<>(), new ArrayList<>());
    List<FireDTO> fireDTOList = new ArrayList<>();
    fireDTOList.add(fireDTO);

    when(mapServiceMock.convertToFireDTOList(personList))
        .thenReturn(fireDTOList);

    FireDTOByAddress personInfoByAddress = new FireDTOByAddress("1 rue antonio vivaldi",
        fireDTOList);
    List<FireDTOByAddress> personInfoByAddressList = new ArrayList<>();
    personInfoByAddressList.add(personInfoByAddress);

    List<FireDTOByAddress> personInfoByAddressList2 = communityService.getPersonInfoByAddressList(stationNumberList);

    assertThat(personInfoByAddressList2).isEqualTo(personInfoByAddressList);
  }

}
