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
class FireTest {

  private Fire fire;
  private List<FireDTO> fireDTOList;
  List<Integer> firestationNumberList;

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
    FireDTO fireDTO2 = new FireDTO("Morabito", 41,
        "05656468", medications2, allergies2);
    fireDTOList = new ArrayList<>();
    fireDTOList.add(fireDTO);
    fireDTOList.add(fireDTO2);
    firestationNumberList = new ArrayList<>();
    fire = new Fire(fireDTOList, firestationNumberList);
  }

  @Test
  public void testGetChildList() throws Exception {
    List<String> medications3 = new ArrayList<>();
    List<String> allergies3 = new ArrayList<>();
    allergies3.add("pollen");
    FireDTO fireDTO3 = new FireDTO("Dupond", 71,
        "05658768", medications3, allergies3);
    List<FireDTO> PersonInfoDTOList2 = new ArrayList<>();
    PersonInfoDTOList2.add(fireDTO3);
    fire.setPersonList(PersonInfoDTOList2);
    assertThat(fire.getPersonList()).isEqualTo(PersonInfoDTOList2);
  }

  @Test
  public void testGetFirestationNumberList() throws Exception {
    firestationNumberList.add(1);
    fire.setFirestationNumberList(firestationNumberList);
    assertThat(fire.getFirestationNumberList()).isEqualTo(firestationNumberList);
  }

  @Test
  public void simpleEqualsPersonInfo() {
    EqualsVerifier.simple().forClass(Fire.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "Fire(personList=" + fireDTOList + ", firestationNumberList=" + firestationNumberList
        + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, fire.toString());
  }


}
