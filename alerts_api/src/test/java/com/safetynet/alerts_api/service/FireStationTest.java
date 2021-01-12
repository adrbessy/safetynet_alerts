package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.service.fireStation.FireStationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FireStationTest {

  private FireStationServiceImpl fireStationService;

  @BeforeEach
  private void setUp() {
    fireStationService = new FireStationServiceImpl();
  }

  /**
   * test to get a list of fire station numbers from a list of fireStations.
   * 
   */
  @Test
  public void testGetStationNumberListFromFireStationList() {
    FireStation fireStation1 = new FireStation();
    fireStation1.setId((long) 5);
    fireStation1.setAddress("82 Alexander Road");
    fireStation1.setStation(6);
    FireStation fireStation2 = new FireStation();
    fireStation2.setId((long) 2);
    fireStation2.setAddress("1 rue Antonio Vivaldi");
    fireStation2.setStation(2);
    List<FireStation> fireStationList = new ArrayList<>();
    fireStationList.add(fireStation1);
    fireStationList.add(fireStation2);
    List<Integer> stationNumberList = new ArrayList<>();
    stationNumberList.add(6);
    stationNumberList.add(2);
    List<Integer> result = fireStationService.getStationNumberListFromFireStationList(fireStationList);
    assertThat(result).isEqualTo(stationNumberList);
  }

}
