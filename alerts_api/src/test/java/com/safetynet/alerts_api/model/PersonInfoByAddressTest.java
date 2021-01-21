package com.safetynet.alerts_api.model;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import nl.jqno.equalsverifier.EqualsVerifier;

@WebMvcTest(controllers = PersonInfoByAddress.class)
class PersonInfoByAddressTest {

  private PersonInfoByAddress personInfoByAddress;
  List<PersonInfoDTO> personInfoDTOList;
  String address;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    List<String> medications = new ArrayList<>();
    medications.add("DAFALGAN");
    List<String> allergies = new ArrayList<>();
    PersonInfoDTO personInfoDTO = new PersonInfoDTO("Bessy", 6,
        "05604660", medications, allergies);
    List<String> medications2 = new ArrayList<>();
    List<String> allergies2 = new ArrayList<>();
    allergies2.add("pollen");
    allergies2.add("chat");
    PersonInfoDTO personInfoDTO2 = new PersonInfoDTO("Morabito", 41,
        "05656468", medications2, allergies2);
    personInfoDTOList = new ArrayList<>();
    personInfoDTOList.add(personInfoDTO);
    personInfoDTOList.add(personInfoDTO2);
    address = "12 rue des ecoles";
    personInfoByAddress = new PersonInfoByAddress(address, personInfoDTOList);
  }

  @Test
  public void testSetAndGetPersonList() throws Exception {
    PersonInfoDTO personInfoDTO3 = new PersonInfoDTO("Durand", 61,
        "0565468", new ArrayList<>(), new ArrayList<>());
    personInfoDTOList.add(personInfoDTO3);
    personInfoByAddress.setPersonList(personInfoDTOList);
    assertThat(personInfoByAddress.getPersonList()).isEqualTo(personInfoDTOList);
  }

  @Test
  public void testSetAndGetAddress() throws Exception {
    personInfoByAddress.setAddress("1 rue antonio vivaldi");
    assertThat(personInfoByAddress.getAddress()).isEqualTo("1 rue antonio vivaldi");
  }

  @Test
  public void simpleEqualsPersonInfoByAddress() {
    EqualsVerifier.simple().forClass(PersonInfoByAddress.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "PersonInfoByAddress(address=" + address + ", personList=" + personInfoDTOList + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, personInfoByAddress.toString());
  }

}
