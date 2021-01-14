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

@WebMvcTest(controllers = Home.class)
class HomeTest {

  private Home home;
  List<Person> childList;
  List<Person> adultList;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    Person adult = new Person();
    Person children = new Person();
    adult.setId((long) 56);
    adult.setFirstName("Adrien");
    adult.setLastName("Bessy");
    adult.setAddress("82 Alexander Road");
    adult.setCity("New York");
    adultList = new ArrayList<>();
    adultList.add(adult);
    children.setId((long) 97);
    children.setFirstName("Christian");
    children.setLastName("Legal");
    children.setAddress("12 rue des ecoles");
    children.setCity("Paris");
    childList = new ArrayList<>();
    childList.add(children);
    home = new Home(childList, adultList);
  }

  @Test
  public void testSetChildList() throws Exception {
    home.setChildList(childList);
    assertThat(home.getChildList()).isEqualTo(childList);
  }

  @Test
  public void testSetAdultList() throws Exception {
    home.setAdultList(adultList);
    assertThat(home.getAdultList()).isEqualTo(adultList);
  }

  @Test
  public void simpleEqualsHome() {
    EqualsVerifier.simple().forClass(Home.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "Home(childList=" + childList + ", adultList=" + adultList + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, home.toString());
  }

}
