package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonNumberInfo;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.person.PersonServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  private PersonServiceImpl personService;

  @Mock
  private FireStationRepository firestationRepository;

  @Mock
  private AddressServiceImpl addressServiceMock;

  @Mock
  private PersonRepository personRepositoryMock;

  // private Person person;

  @BeforeEach
  private void setUp() {
    personService = new PersonServiceImpl();
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


    when(firestationRepository.findDistinctByStation(org.mockito.ArgumentMatchers.anyInt()))
        .thenReturn(fireStationList);
    when(addressServiceMock.getAddressListFromFireStationList(fireStationList)).thenReturn(addressList);
    when(personRepositoryMock.findAllByAddressInOrderByAddress(addressList)).thenReturn(personList);
    List<Person> result = personService.getPersonListFromStationNumber(1);

    // THEN

    assertThat(result).isEqualTo(personList);
    verify(personRepositoryMock, Mockito.times(1)).findAllByAddressInOrderByAddress(addressList);

  }

  /**
   * test to get a list of person from a list of fireStation number.
   * 
   */
  @Test
  public void testAddAddressToListFromFireStationList() {/*
                                                          * Person person = new Person();
                                                          * person.setEmail("ballapolorra-7977@yopmail.com");
                                                          * person.setAddress("82 Alexander Road"); MedicalRecord
                                                          * medicalRecord1 = new MedicalRecord();
                                                          * medicalRecord1.setBirthdate("16/06/2020");
                                                          * List<MedicalRecord> medicalRecordList = new ArrayList<>();
                                                          * medicalRecordList.add(medicalRecord1); List<Person>
                                                          * childrenList = new ArrayList<>(); List<Person> adultList =
                                                          * new ArrayList<>(); List<Person> childrenListTest = new
                                                          * ArrayList<>(); childrenListTest.add(person); //
                                                          * when(person.setAge_Medications_Allergies(medicalRecord1,
                                                          * LocalDate.of(2021, // 1, 11)));
                                                          * personService.addAddressToListFromFireStationList(person,
                                                          * medicalRecordList, childrenList, adultList,
                                                          * LocalDate.of(2021, 1, 11));
                                                          * assertThat(childrenList).isEqualTo(childrenListTest);
                                                          */
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

    when(firestationRepository.findDistinctByStation(Mockito.anyInt())).thenReturn(fireStationList);

    List<PersonNumberInfo> personNumberInfoList = personService.getPersonNumberInfoListFromStationNumber(1);
    assertThat(personNumberInfoList).isEqualTo(null);
  }

}
