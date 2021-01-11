package com.safetynet.alerts_api.model;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = MedicalRecord.class)
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

}
