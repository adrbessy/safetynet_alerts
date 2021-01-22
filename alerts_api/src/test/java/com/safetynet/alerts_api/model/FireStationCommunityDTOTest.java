package com.safetynet.alerts_api.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest()
public class FireStationCommunityDTOTest {

  @Test
  public void simpleEqualsPersonInfoDTO() {
    EqualsVerifier.simple().forClass(FireStationCommunityDTO.class).verify();
  }

}
