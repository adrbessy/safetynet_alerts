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

@SpringBootTest()
class FireDTOByAddressTest {

  private FireDTOByAddress fireDTOByAddress;
  List<FireDTO> fireDTOList;
  String address;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    List<String> medications = new ArrayList<>();
    medications.add("DAFALGAN");
    List<String> allergies = new ArrayList<>();
    FireDTO fireDTO = new FireDTO("Bessy", 6,
        "05604660", medications, allergies);
    List<String> medications2 = new ArrayList<>();
    List<String> allergies2 = new ArrayList<>();
    allergies2.add("pollen");
    allergies2.add("chat");
    FireDTO personInfoDTO2 = new FireDTO("Morabito", 41,
        "05656468", medications2, allergies2);
    fireDTOList = new ArrayList<>();
    fireDTOList.add(fireDTO);
    fireDTOList.add(personInfoDTO2);
    address = "12 rue des ecoles";
    fireDTOByAddress = new FireDTOByAddress(address, fireDTOList);
  }

  @Test
  public void testSetAndGetPersonList() throws Exception {
    FireDTO personInfoDTO3 = new FireDTO("Durand", 61,
        "0565468", new ArrayList<>(), new ArrayList<>());
    fireDTOList.add(personInfoDTO3);
    fireDTOByAddress.setPersonList(fireDTOList);
    assertThat(fireDTOByAddress.getPersonList()).isEqualTo(fireDTOList);
  }

  @Test
  public void testSetAndGetAddress() throws Exception {
    fireDTOByAddress.setAddress("1 rue antonio vivaldi");
    assertThat(fireDTOByAddress.getAddress()).isEqualTo("1 rue antonio vivaldi");
  }

  @Test
  public void simpleEqualsPersonInfoByAddress() {
    EqualsVerifier.simple().forClass(FireDTOByAddress.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "FireDTOByAddress(address=" + address + ", personList=" + fireDTOList + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, fireDTOByAddress.toString());
  }

}
