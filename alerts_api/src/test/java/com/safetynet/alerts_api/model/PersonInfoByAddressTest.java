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

@WebMvcTest(controllers = PersonInfoByAddress.class)
class PersonInfoByAddressTest {

  private PersonInfoByAddress personInfoByAddress;
  List<Person> personList;
  String address;

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
    address = "12 rue des ecoles";
    personInfoByAddress = new PersonInfoByAddress(address, personList);
  }

  @Test
  public void testSetAndGetPersonList() throws Exception {
    Person person3 = new Person();
    person3.setId((long) 98);
    personList.add(person3);
    personInfoByAddress.setPersonList(personList);
    assertThat(personInfoByAddress.getPersonList()).isEqualTo(personList);
  }

  @Test
  public void testSetAndGetAddress() throws Exception {
    personInfoByAddress.setAddress("1 rue antonio vivaldi");
    assertThat(personInfoByAddress.getAddress()).isEqualTo("1 rue antonio vivaldi");
  }

  @Test
  public void simpleEqualsPersonInfoByAddress() {
    EqualsVerifier.simple().forClass(PersonInfoByAddress.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "PersonInfoByAddress(address=" + address + ", personList=" + personList + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, personInfoByAddress.toString());
  }

}
