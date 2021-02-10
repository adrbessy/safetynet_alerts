package com.safetynet.alerts_api.model;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SpringBootTest()
class PersonTest {

  private Person person;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    person = new Person();
  }

  @Test
  public void testGetId() throws Exception {
    person.setId((long) 56);
    assertThat(person.getId()).isEqualTo(56);
  }

  @Test
  public void testGetFirstName() throws Exception {
    person.setFirstName("Adrien");
    assertThat(person.getFirstName()).isEqualTo("Adrien");
  }

  @Test
  public void testGetLastName() throws Exception {
    person.setLastName("Bessy");
    assertThat(person.getLastName()).isEqualTo("Bessy");
  }

  @Test
  public void testGetAddress() throws Exception {
    person.setAddress("82 Alexander Road");
    assertThat(person.getAddress()).isEqualTo("82 Alexander Road");
  }

  @Test
  public void testGetCity() throws Exception {
    person.setCity("New York");
    assertThat(person.getCity()).isEqualTo("New York");
  }

  @Test
  public void testGetZip() throws Exception {
    person.setZip("1648462");
    assertThat(person.getZip()).isEqualTo("1648462");
  }

  @Test
  public void testGetPhone() throws Exception {
    person.setPhone("04815644");
    assertThat(person.getPhone()).isEqualTo("04815644");
  }

  @Test
  public void testGetEmail() throws Exception {
    person.setEmail("uttoxuballo-8128@yopmail.com");
    assertThat(person.getEmail()).isEqualTo("uttoxuballo-8128@yopmail.com");
  }

  @Test
  public void testGetMedications() throws Exception {
    List<String> med = new ArrayList<>();
    med.add("odoxadin:30mg");
    med.add("aznol:200mg");
    person.setMedications(med);
    assertThat(person.getMedications()).isEqualTo(med);
  }

  @Test
  public void testGetAllergies() throws Exception {
    List<String> all = new ArrayList<>();
    all.add("nillacilan");
    person.setAllergies(all);
    assertThat(person.getAllergies()).isEqualTo(all);
  }

  @Test
  public void testSetAge() throws Exception {
    person.setAge("16/06/1988", LocalDate.of(2021, 1, 11));
    assertThat(person.getAge()).isEqualTo(32);
  }

  @Test
  public void testSetMedicationsAndAllergies() throws Exception {
    MedicalRecord medicalRecord = new MedicalRecord();
    List<String> aller = new ArrayList<>();
    aller.add("cacahuete");
    medicalRecord.setAllergies(aller);
    List<String> medic = new ArrayList<>();
    medic.add("dolipran:30mg");
    medic.add("aznol:200mg");
    medicalRecord.setMedications(medic);
    person.setMedicationsAndAllergies(medicalRecord);
    assertThat(person.getAllergies()).isEqualTo(aller);
    assertThat(person.getMedications()).isEqualTo(medic);
  }


  @Test
  public void simpleEqualsPerson() {
    EqualsVerifier.forClass(Person.class).suppress(Warning.ALL_FIELDS_SHOULD_BE_USED).verify();
  }

}
