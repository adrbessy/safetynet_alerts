package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.safetynet.alerts_api.service.childrenAdults.ChildrenAdultsService;
import com.safetynet.alerts_api.service.fireStationCommunity.FireStationCommunityService;
import com.safetynet.alerts_api.service.map.MapService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class FireStationCommunityServiceTest {

  @Autowired
  private FireStationCommunityService fireStationCommunityService;

  @MockBean
  private FireStationRepository firestationRepository;

  @MockBean
  private AddressServiceImpl addressServiceMock;

  @MockBean
  private ChildrenAdultsService ChildrenAdultsServiceMock;

  @MockBean
  private PersonRepository personRepositoryMock;

  @MockBean
  private MedicalRecordRepository medicalRecordRepositoryMock;

  @MockBean
  private MapService mapServiceMock;

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
    List<Person> childrenList = new ArrayList<>();
    childrenList.add(person);
    List<Person> adultList = new ArrayList<>();
    Map<String, List<Person>> map = new HashMap<String, List<Person>>();
    map.put("childrenList", childrenList);
    map.put("adultList", adultList);

    when(firestationRepository.findDistinctByStation(org.mockito.ArgumentMatchers
        .anyInt())).thenReturn(fireStationList);
    when(addressServiceMock.getAddressListFromFireStationList(fireStationList)).thenReturn(addressList);
    when(personRepositoryMock.findAllByAddressInOrderByAddress(addressList)).thenReturn(personList);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setBirthdate("16/06/2020");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalRecordList.add(medicalRecord1);

    // when(medicalRecordRepositoryMock.findByFirstNameAndLastNameAllIgnoreCase("Adrien",
    // "Bessy")).thenReturn(medicalRecordList);
    when(ChildrenAdultsServiceMock.fullChildrenListAndAdultListFromPersonList(personList,
        childrenList, adultList)).thenReturn(map);

    FireStationCommunityDTO fireStationCommunityDTO = new FireStationCommunityDTO("Adrien", "Bessy", "", "", "",
        "");
    List<FireStationCommunityDTO> fireStationCommunityDTOList = new ArrayList<>();
    fireStationCommunityDTOList.add(fireStationCommunityDTO);
    when(mapServiceMock.convertToFireStationCommunityDTOList(personList))
        .thenReturn(fireStationCommunityDTOList);

    FireStationCommunity fireStationCommunity = new FireStationCommunity(fireStationCommunityDTOList,
        1, 0);

    FireStationCommunity fireStationCommunity2 = fireStationCommunityService
        .getPersonNumberInfoListFromStationNumber(1);
    assertThat(fireStationCommunity2).isEqualTo(fireStationCommunity);
  }

  /**
   * test to get a list of PersonNumberInfo from a fireStation number.
   * 
   */
  @Test
  public void testGetPersonNumberInfoListFromStationNumberIfStationNumberIsNull() {

    FireStationCommunity fireStationCommunity = fireStationCommunityService
        .getPersonNumberInfoListFromStationNumber(null);
    assertThat(fireStationCommunity).isEqualTo(null);
  }

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
    List<Person> result = fireStationCommunityService.getPersonListFromStationNumber(1);

    // THEN
    assertThat(result).isEqualTo(personList);
    verify(personRepositoryMock,
        Mockito.times(1)).findAllByAddressInOrderByAddress(addressList);

  }

}
