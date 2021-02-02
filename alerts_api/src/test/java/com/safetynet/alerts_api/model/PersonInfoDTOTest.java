package com.safetynet.alerts_api.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest()
public class PersonInfoDTOTest {

  private PersonInfoDTO personInfoDTO;
  String lastName;
  int age;
  String address;
  String city;
  String zip;
  String email;
  List<String> medications;
  List<String> allergies;

  @BeforeEach
  private void setUp() {
    medications = new ArrayList<>();
    medications.add("DAFALGAN");
    allergies = new ArrayList<>();
    lastName = "Bessy";
    age = 45;
    address = "1 rue antonio vivaldi";
    city = "Paris";
    zip = "65465";
    email = "afqgg@mail.fr";
    personInfoDTO = new PersonInfoDTO(lastName, age,
        address, city, zip, email, medications, allergies);
  }

  @Test
  public void simpleEqualsPersonInfoDTO() {
    EqualsVerifier.simple().forClass(PersonInfoDTO.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "PersonInfoDTO(lastName=" + lastName + ", age=" + age + ", address=" + address + ", city="
        + city + ", zip=" + zip + ", email=" + email + ", medications=" + medications + ", allergies=" + allergies
        + ")";
    Assert.assertEquals(expected, personInfoDTO.toString());
  }

  @Test
  public void testSetLastName() throws Exception {
    personInfoDTO.setLastName("Robert");
    assertThat(personInfoDTO.getLastName()).isEqualTo("Robert");
  }

  @Test
  public void testSetAge() throws Exception {
    personInfoDTO.setAge(8);
    assertThat(personInfoDTO.getAge()).isEqualTo(8);
  }

  @Test
  public void testSetAddress() throws Exception {
    personInfoDTO.setAddress("54 road stone");
    assertThat(personInfoDTO.getAddress()).isEqualTo("54 road stone");
  }

  @Test
  public void testSetCity() throws Exception {
    personInfoDTO.setCity("stone");
    assertThat(personInfoDTO.getCity()).isEqualTo("stone");
  }

  @Test
  public void testSetZip() throws Exception {
    personInfoDTO.setZip("454");
    assertThat(personInfoDTO.getZip()).isEqualTo("454");
  }

  @Test
  public void testSetEmail() throws Exception {
    personInfoDTO.setEmail("abcd@gmail.com");
    assertThat(personInfoDTO.getEmail()).isEqualTo("abcd@gmail.com");
  }

  @Test
  public void testSetMedications() throws Exception {
    medications = new ArrayList<>();
    medications.add("aspirine");
    personInfoDTO.setMedications(medications);
    assertThat(personInfoDTO.getMedications()).isEqualTo(medications);
  }

  @Test
  public void testSetAllergies() throws Exception {
    allergies = new ArrayList<>();
    allergies.add("something");
    personInfoDTO.setAllergies(allergies);
    assertThat(personInfoDTO.getAllergies()).isEqualTo(allergies);
  }

}
