package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.service.address.AddressService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AddressServiceTest {

  @Autowired
  private AddressService addressService;

  @MockBean
  private FireStationRepository firestationRepositoryMock;

  @MockBean
  private FireStation firestationMock;

  private FireStation fireStation1;

  private List<FireStation> fireStationList;

  private List<String> addressList1;

  @BeforeEach
  private void setUp() {
    fireStation1 = new FireStation();
    fireStation1.setId((long) 5);
    fireStation1.setAddress("82 Alexander Road");
    fireStation1.setStation(6);
    fireStationList = new ArrayList<>();
    fireStationList.add(fireStation1);
    addressList1 = new ArrayList<>();
    addressList1.add("82 Alexander Road");
  }

  /**
   * test to get a list of address from a list of fire station number.
   * 
   */
  @Test
  public void testGetAddressListFromStationNumberList() {
    List<Integer> stationNumberList = new ArrayList<>();
    stationNumberList.add(4);

    when(firestationRepositoryMock.findDistinctByStation(4)).thenReturn(fireStationList);
    when(firestationMock.getAddress()).thenReturn("82 Alexander Road");

    List<String> addressList2 = addressService.getAddressListFromStationNumberList(stationNumberList);
    assertThat(addressList2).isEqualTo(addressList1);
  }

  /**
   * test to loop based on fireStation number.
   * 
   */
  @Test
  public void testAddAddressToListFromFireStationList() {
    List<String> addressList = new ArrayList<>();
    FireStation fireStation2 = new FireStation();
    fireStation2.setId((long) 2);
    fireStation2.setAddress("1 rue Antonio Vivaldi");
    fireStation2.setStation(6);
    fireStationList.add(fireStation2);
    addressList1.add("1 rue Antonio Vivaldi");

    addressService.addAddressToListFromFireStationList(fireStationList, addressList);
    assertThat(addressList).isEqualTo(addressList1);
  }

  /**
   * test to get a list of address from a list of fire station.
   * 
   */
  @Test
  public void testGetAddressListFromFireStationList() {
    FireStation fireStation2 = new FireStation();
    fireStation2.setId((long) 2);
    fireStation2.setAddress("1 rue Antonio Vivaldi");
    fireStation2.setStation(6);
    fireStationList.add(fireStation2);
    addressList1.add("1 rue Antonio Vivaldi");

    List<String> result = addressService.getAddressListFromFireStationList(fireStationList);
    assertThat(result).isEqualTo(addressList1);
  }

}
