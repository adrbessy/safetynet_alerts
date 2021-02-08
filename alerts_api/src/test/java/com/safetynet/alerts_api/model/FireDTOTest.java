package com.safetynet.alerts_api.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest()
public class FireDTOTest {

  private FireDTO fireDTO;

  @BeforeEach
  private void setUp() {
    fireDTO = new FireDTO("", 8,
        "", new ArrayList<>(), new ArrayList<>());
  }

  @Test
  public void simpleEqualsFireDTO() {
    EqualsVerifier.simple().forClass(FireDTO.class).verify();
  }

  @Test
  public void testSetLastName() throws Exception {
    fireDTO.setLastName("Robert");
    assertThat(fireDTO.getLastName()).isEqualTo("Robert");
  }

  @Test
  public void testSetPhone() throws Exception {
    fireDTO.setPhone("45445");
    assertThat(fireDTO.getPhone()).isEqualTo("45445");
  }

  @Test
  public void testSetAge() throws Exception {
    fireDTO.setAge(8);
    assertThat(fireDTO.getAge()).isEqualTo(8);
  }


  @Test
  public void testSetMedications() throws Exception {
    List<String> medications;
    medications = new ArrayList<>();
    medications.add("aspirine");
    fireDTO.setMedications(medications);
    assertThat(fireDTO.getMedications()).isEqualTo(medications);
  }

  @Test
  public void testSetAllergies() throws Exception {
    List<String> allergies;
    allergies = new ArrayList<>();
    allergies.add("something");
    fireDTO.setAllergies(allergies);
    assertThat(fireDTO.getAllergies()).isEqualTo(allergies);
  }

}
