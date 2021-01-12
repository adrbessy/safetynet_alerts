package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class AddressServiceTest {

  private AddressServiceImpl addressService;

  @Mock
  private FireStationRepository firestationRepository;

  @BeforeEach
  private void setUp() {
    addressService = new AddressServiceImpl();
  }

  /**
   * test to get a list of address from a list of fire station number.
   * 
   */
  @Test
  public void testGetAddressListFromStationNumberList() {
    /*
     * List<Integer> stationNumberList = new ArrayList<>();
     * stationNumberList.add(4); stationNumberList.add(5);
     * when(firestationRepository.findDistinctByStation(any()).thenReturn(1);
     * List<String> addressListNoDuplicates =
     * addressService.getAddressListFromStationNumberList(stationNumberList);
     */
  }

  /**
   * test to loop based on fireStation number.
   * 
   */
  @Test
  public void testAddAddressToListFromFireStationList() {
    List<String> addressList = new ArrayList<>();
    FireStation fireStation1 = new FireStation();
    fireStation1.setId((long) 5);
    fireStation1.setAddress("82 Alexander Road");
    fireStation1.setStation(6);
    FireStation fireStation2 = new FireStation();
    fireStation2.setId((long) 2);
    fireStation2.setAddress("1 rue Antonio Vivaldi");
    fireStation2.setStation(6);
    List<FireStation> fireStationList = new ArrayList<>();
    fireStationList.add(fireStation1);
    fireStationList.add(fireStation2);
    List<String> addressList2 = new ArrayList<>();
    addressList2.add("82 Alexander Road");
    addressList2.add("1 rue Antonio Vivaldi");
    addressService.addAddressToListFromFireStationList(fireStationList, addressList);
    assertThat(addressList).isEqualTo(addressList2);
  }

  /**
   * test to get a list of address from a list of fire station.
   * 
   */
  @Test
  public void testGetAddressListFromFireStationList() {
    FireStation fireStation1 = new FireStation();
    fireStation1.setId((long) 5);
    fireStation1.setAddress("82 Alexander Road");
    fireStation1.setStation(6);
    FireStation fireStation2 = new FireStation();
    fireStation2.setId((long) 2);
    fireStation2.setAddress("1 rue Antonio Vivaldi");
    fireStation2.setStation(6);
    List<FireStation> fireStationList = new ArrayList<>();
    fireStationList.add(fireStation1);
    fireStationList.add(fireStation2);
    List<String> addressList = new ArrayList<>();
    addressList.add("82 Alexander Road");
    addressList.add("1 rue Antonio Vivaldi");
    List<String> result = addressService.getAddressListFromFireStationList(fireStationList);
    assertThat(result).isEqualTo(addressList);
  }

}
