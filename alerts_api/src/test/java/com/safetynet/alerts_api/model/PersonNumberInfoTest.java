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

@WebMvcTest(controllers = PersonNumberInfo.class)
class PersonNumberInfoTest {

  private PersonNumberInfo personNumberInfo;
  List<Person> personList;
  private int numberOfChildren;
  private int numberOfAdults;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    Person person = new Person();
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
    numberOfChildren = 4;
    numberOfAdults = 4;
    personNumberInfo = new PersonNumberInfo(personList, numberOfChildren, numberOfAdults);
  }

  @Test
  public void testGetPersonList() throws Exception {
    assertThat(personNumberInfo.getPersonList()).isEqualTo(personList);
  }

  @Test
  public void testGetNumberOfChildren() throws Exception {
    assertThat(personNumberInfo.getNumberOfChildren()).isEqualTo(numberOfChildren);
  }

  @Test
  public void testGetNumberOfAdults() throws Exception {
    assertThat(personNumberInfo.getNumberOfAdults()).isEqualTo(numberOfAdults);
  }

  @Test
  public void simpleEqualsHome() {
    EqualsVerifier.simple().forClass(PersonNumberInfo.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "PersonNumberInfo(personList=" + personList + ", numberOfChildren=" + numberOfChildren
        + ", numberOfAdults=" + numberOfAdults + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, personNumberInfo.toString());
  }

}
