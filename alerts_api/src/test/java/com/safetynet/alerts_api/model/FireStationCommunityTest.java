package com.safetynet.alerts_api.model;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest()
class FireStationCommunityTest {

  private FireStationCommunity fireStationCommunity;
  List<FireStationCommunityDTO> fireStationCommunityDTOList;
  private int numberOfChildren;
  private int numberOfAdults;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    FireStationCommunityDTO fireStationCommunityDTO = new FireStationCommunityDTO("Adrien",
        "Bessy",
        "82 Alexander Road",
        "Londres",
        "15465", "0689454515");
    FireStationCommunityDTO fireStationCommunityDTO2 = new FireStationCommunityDTO("Julie",
        "Garnier",
        "8 Alexander Road",
        "New York",
        "15645", "0689445485");
    fireStationCommunityDTOList = new ArrayList<>();
    fireStationCommunityDTOList.add(fireStationCommunityDTO);
    fireStationCommunityDTOList.add(fireStationCommunityDTO2);
    numberOfChildren = 4;
    numberOfAdults = 4;
    fireStationCommunity = new FireStationCommunity(fireStationCommunityDTOList, numberOfChildren, numberOfAdults);
  }

  @Test
  public void testGetPersonList() throws Exception {
    assertThat(fireStationCommunity.getPersonList()).isEqualTo(fireStationCommunityDTOList);
  }

  @Test
  public void testGetNumberOfChildren() throws Exception {
    assertThat(fireStationCommunity.getNumberOfChildren()).isEqualTo(numberOfChildren);
  }

  @Test
  public void testGetNumberOfAdults() throws Exception {
    assertThat(fireStationCommunity.getNumberOfAdults()).isEqualTo(numberOfAdults);
  }

  @Test
  public void simpleEqualsHome() {
    EqualsVerifier.simple().forClass(FireStationCommunity.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "FireStationCommunity(personList=" + fireStationCommunityDTOList + ", numberOfChildren="
        + numberOfChildren
        + ", numberOfAdults=" + numberOfAdults + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, fireStationCommunity.toString());
  }

}
