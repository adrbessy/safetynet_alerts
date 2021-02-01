package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.service.childrenAdults.ChildrenAdultsService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ChildrenAdultsServiceTest {

  @Autowired
  private ChildrenAdultsService ChildrenAdultsService;

  @MockBean
  private MedicalRecordRepository medicalRecordRepositoryMock;

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

    ChildrenAdultsService.fullChildrenListAndAdultListFromPersonList(personList, childrenList, adultList);

    assertThat(childrenList).isEqualTo(childrenList2);
  }


}
