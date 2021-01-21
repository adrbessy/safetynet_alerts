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
  List<ChildAlertDTO> childList;
  List<ChildAlertDTO> adultList;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    ChildAlertDTO adult = new ChildAlertDTO("Bessy", "Adrien", 51);
    ChildAlertDTO children = new ChildAlertDTO("Bernard", "Paul", 11);
    ChildAlertDTO adult2 = new ChildAlertDTO("Poulet", "Adrien", 78);
    ChildAlertDTO children2 = new ChildAlertDTO("Bessy", "marie", 14);
    adultList = new ArrayList<>();
    adultList.add(adult);
    adultList.add(adult2);
    childList = new ArrayList<>();
    childList.add(children);
    childList.add(children2);
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
