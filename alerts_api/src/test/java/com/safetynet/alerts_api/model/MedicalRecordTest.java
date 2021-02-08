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
import nl.jqno.equalsverifier.Warning;

@SpringBootTest()
class MedicalRecordTest {

  private MedicalRecord medicalRecord;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    medicalRecord = new MedicalRecord();
  }

  @Test
  public void testGetId() throws Exception {
    medicalRecord.setId((long) 56);
    assertThat(medicalRecord.getId()).isEqualTo(56);
  }

  @Test
  public void testGetFirstName() throws Exception {
    medicalRecord.setFirstName("Adrien");
    assertThat(medicalRecord.getFirstName()).isEqualTo("Adrien");
  }

  @Test
  public void testGetLastName() throws Exception {
    medicalRecord.setLastName("Bessy");
    assertThat(medicalRecord.getLastName()).isEqualTo("Bessy");
  }

  @Test
  public void testGetBirthdateName() throws Exception {
    medicalRecord.setBirthdate("16/06/1988");
    assertThat(medicalRecord.getBirthdate()).isEqualTo("16/06/1988");
  }

  @Test
  public void testGetMedications() throws Exception {
    List<String> med = new ArrayList<>();
    med.add("odoxadin:30mg");
    med.add("aznol:200mg");
    medicalRecord.setMedications(med);
    assertThat(medicalRecord.getMedications()).isEqualTo(med);
  }

  @Test
  public void testGetAllergies() throws Exception {
    List<String> all = new ArrayList<>();
    all.add("nillacilan");
    medicalRecord.setAllergies(all);
    assertThat(medicalRecord.getAllergies()).isEqualTo(all);
  }

  @Test
  public void simpleEqualsMedicalRecord() {
    EqualsVerifier.forClass(MedicalRecord.class).suppress(Warning.ALL_FIELDS_SHOULD_BE_USED).verify();
  }

  @Test
  public void testToString() {
    medicalRecord.setId((long) 56);
    String expected = "MedicalRecord(id=56, firstName=null, lastName=null, birthdate=null, medications=null, allergies=null)";
    ; // put the expected value here
    Assert.assertEquals(expected, medicalRecord.toString());
  }

}
