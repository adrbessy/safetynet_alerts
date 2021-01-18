package com.safetynet.alerts_api.model;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import nl.jqno.equalsverifier.EqualsVerifier;

@WebMvcTest(controllers = PersonInfo.class)
class PersonInfoTest {

  private PersonInfo personInfo;
  List<Person> personList;
  List<Integer> firestationNumberList;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    Person person = new Person();
    firestationNumberList = new ArrayList<Integer>();
    firestationNumberList.add(4);
    firestationNumberList.add(5);
    person.setId((long) 56);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setAddress("82 Alexander Road");
    person.setCity("New York");
    personList = new ArrayList<>();
    personList.add(person);
    Person person2 = new Person();
    person2.setId((long) 97);
    person2.setFirstName("Christian");
    person2.setLastName("Legal");
    person2.setAddress("12 rue des ecoles");
    person2.setCity("Paris");
    personList.add(person2);
    personInfo = new PersonInfo(personList, firestationNumberList);
  }

  @Test
  public void testGetChildList() throws Exception {
    Person person3 = new Person();
    person3.setId((long) 98);
    personList.add(person3);
    personInfo.setPersonList(personList);
    assertThat(personInfo.getPersonList()).isEqualTo(personList);
  }

  @Test
  public void testGetFirestationNumberList() throws Exception {
    firestationNumberList.add(1);
    personInfo.setFirestationNumberList(firestationNumberList);
    assertThat(personInfo.getFirestationNumberList()).isEqualTo(firestationNumberList);
  }

  @Test
  public void simpleEqualsPersonInfo() {
    EqualsVerifier.simple().forClass(PersonInfo.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "PersonInfo(personList=" + personList + ", firestationNumberList=" + firestationNumberList + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, personInfo.toString());
  }


}
