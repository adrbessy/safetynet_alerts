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

@WebMvcTest(controllers = PersonInfo.class)
class PersonInfoTest {

  private PersonInfo personInfo;
  private List<PersonInfoDTO> personInfoDTOList;
  List<Integer> firestationNumberList;

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
    firestationNumberList = new ArrayList<>();
    personInfo = new PersonInfo(personInfoDTOList, firestationNumberList);
  }

  @Test
  public void testGetChildList() throws Exception {
    List<String> medications3 = new ArrayList<>();
    List<String> allergies3 = new ArrayList<>();
    allergies3.add("pollen");
    PersonInfoDTO personInfoDTO3 = new PersonInfoDTO("Dupond", 71,
        "05658768", medications3, allergies3);
    List<PersonInfoDTO> PersonInfoDTOList2 = new ArrayList<>();
    PersonInfoDTOList2.add(personInfoDTO3);
    personInfo.setPersonList(PersonInfoDTOList2);
    assertThat(personInfo.getPersonList()).isEqualTo(PersonInfoDTOList2);
  }

  @Test
  public void testGetFirestationNumberList() throws Exception {
    firestationNumberList.add(1);
    personInfo.setFirestationNumberList(firestationNumberList);
    assertThat(personInfo.getFirestationNumberList()).isEqualTo(firestationNumberList);
  }

  @Test
  public void simpleEqualsPersonInfo() {
    EqualsVerifier.simple().forClass(PersonInfo.class).verify();
  }

  @Test
  public void testToString() {
    String expected = "PersonInfo(personList=" + personInfoDTOList + ", firestationNumberList=" + firestationNumberList
        + ")";
    ; // put the expected value here
    Assert.assertEquals(expected, personInfo.toString());
  }


}
