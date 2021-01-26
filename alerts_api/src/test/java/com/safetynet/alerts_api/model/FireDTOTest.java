package com.safetynet.alerts_api.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest()
public class FireDTOTest {

  @Test
  public void simpleEqualsFireDTO() {
    EqualsVerifier.simple().forClass(FireDTO.class).verify();
  }

}
