package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.FireDTO;
import com.safetynet.alerts_api.model.FireDTOByAddress;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.flood.FloodService;
import com.safetynet.alerts_api.service.map.MapService;
import com.safetynet.alerts_api.service.personInfo.PersonInfoServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class FloodServiceTest {

  @Autowired
  private FloodService floodService;

  @MockBean
  private AddressServiceImpl addressServiceMock;

  @MockBean
  private PersonRepository personRepositoryMock;

  @MockBean
  private PersonInfoServiceImpl personInfoServiceMock;

  @MockBean
  private MedicalRecordRepository medicalRecordRepositoryMock;

  @MockBean
  private MedicalRecord medicalRecordMock;

  @MockBean
  private Person personMock;

  @MockBean
  private MapService mapServiceMock;

  /**
   * test to get a PersonInfo by address from a station number List.
   * 
   */
  @Test
  public void testGetPersonInfoByAddressList() {
    List<Integer> stationNumberList = new ArrayList<>();
    stationNumberList.add(4);
    stationNumberList.add(5);
    List<String> addressList = new ArrayList<>();
    addressList.add("1 rue antonio vivaldi");
    when(addressServiceMock.getAddressListFromStationNumberList(stationNumberList)).thenReturn(addressList);

    Person person = new Person();
    person.setId((long) 5);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setAddress("82 Alexander Road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    String address = "1 rue antonio vivaldi";

    when(personRepositoryMock.findDistinctByAddress(address)).thenReturn(personList);
    doNothing().when(personInfoServiceMock).setAgeAndMedicationsAndAllergiesFromPersonList(personList);

    FireDTO fireDTO = new FireDTO("Bessy", 45, "0065468", new ArrayList<>(), new ArrayList<>());
    List<FireDTO> fireDTOList = new ArrayList<>();
    fireDTOList.add(fireDTO);

    when(mapServiceMock.convertToFireDTOList(personList))
        .thenReturn(fireDTOList);

    FireDTOByAddress personInfoByAddress = new FireDTOByAddress("1 rue antonio vivaldi",
        fireDTOList);
    List<FireDTOByAddress> personInfoByAddressList = new ArrayList<>();
    personInfoByAddressList.add(personInfoByAddress);

    List<FireDTOByAddress> personInfoByAddressList2 = floodService.getPersonInfoByAddressList(stationNumberList);

    assertThat(personInfoByAddressList2).isEqualTo(personInfoByAddressList);
  }

}
