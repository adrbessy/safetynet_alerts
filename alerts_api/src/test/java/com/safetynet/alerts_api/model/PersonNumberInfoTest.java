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
  List<PersonNumberInfoDTO> personNumberInfoDTOList;
  private int numberOfChildren;
  private int numberOfAdults;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    PersonNumberInfoDTO personNumberInfoDTO = new PersonNumberInfoDTO("Adrien",
        "Bessy",
        "82 Alexander Road",
        "Londres",
        "15465", "0689454515");
    PersonNumberInfoDTO personNumberInfoDTO2 = new PersonNumberInfoDTO("Julie",
        "Garnier",
        "8 Alexander Road",
        "New York",
        "15645", "0689445485");
    personNumberInfoDTOList = new ArrayList<>();
    personNumberInfoDTOList.add(personNumberInfoDTO);
    personNumberInfoDTOList.add(personNumberInfoDTO2);
    numberOfChildren = 4;
    numberOfAdults = 4;
    personNumberInfo = new PersonNumberInfo(personNumberInfoDTOList, numberOfChildren, numberOfAdults);
  }

  @Test
  public void testGetPersonList() throws Exception {
    assertThat(personNumberInfo.getPersonList()).isEqualTo(personNumberInfoDTOList);
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
    String expected = "PersonNumberInfo(personList=" + personNumberInfoDTOList + ", numberOfChildren="
        + numberOfChildren
        + ", numberOfAdults=" + numberOfAdults + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, personNumberInfo.toString());
  }

}
