package com.safetynet.alerts_api.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest()
public class FireStationCommunityDTOTest {

  private FireStationCommunityDTO fireStationCommunityDTO;

  @BeforeEach
  private void setUp() {
    fireStationCommunityDTO = new FireStationCommunityDTO("", "",
        "", "", "", "");
  }

  @Test
  public void simpleEqualsFireStationCommunityDTO() {
    EqualsVerifier.simple().forClass(FireStationCommunityDTO.class).verify();
  }

  @Test
  public void testSetFirstName() throws Exception {
    fireStationCommunityDTO.setFirstName("Adrien");
    assertThat(fireStationCommunityDTO.getFirstName()).isEqualTo("Adrien");
  }

  @Test
  public void testSetLastName() throws Exception {
    fireStationCommunityDTO.setLastName("Robert");
    assertThat(fireStationCommunityDTO.getLastName()).isEqualTo("Robert");
  }

  @Test
  public void testSetAddress() throws Exception {
    fireStationCommunityDTO.setAddress("54 road stone");
    assertThat(fireStationCommunityDTO.getAddress()).isEqualTo("54 road stone");
  }

  @Test
  public void testSetCity() throws Exception {
    fireStationCommunityDTO.setCity("stone");
    assertThat(fireStationCommunityDTO.getCity()).isEqualTo("stone");
  }

  @Test
  public void testSetZip() throws Exception {
    fireStationCommunityDTO.setZip("454");
    assertThat(fireStationCommunityDTO.getZip()).isEqualTo("454");
  }

  @Test
  public void testSetPhone() throws Exception {
    fireStationCommunityDTO.setPhone("45445");
    assertThat(fireStationCommunityDTO.getPhone()).isEqualTo("45445");
  }

}
