package com.safetynet.alerts_api.model;

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

}
