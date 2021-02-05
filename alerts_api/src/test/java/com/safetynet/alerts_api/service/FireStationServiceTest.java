package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class FireStationServiceTest {

  @Autowired
  private FireStationService fireStationService;

  @MockBean
  private FireStationRepository fireStationRepositoryMock;

  private FireStation fireStation;

  @BeforeEach
  private void setUp() {
    fireStation = new FireStation();
  }

  /**
   * test to get a fire station.
   * 
   */
  @Test
  public void testGetFireStation() {
    String address = "1 rue antonio vivaldi";
    fireStation.setAddress(address);
    Optional<FireStation> optionalFireStation = Optional.of(fireStation);

    when(fireStationRepositoryMock.findByAddress(address)).thenReturn(optionalFireStation);

    FireStation fireStationResult = fireStationService.getFireStation(address);
    assertThat(fireStationResult).isEqualTo(fireStation);
  }


  /**
   * test to save All FireStations if empty list.
   * 
   */
  @Test
  public void testSaveAllFireStationsIfEmptyList() {
    List<FireStation> fireStationsList = new ArrayList<>();

    boolean result = fireStationService.saveAllFireStations(fireStationsList);
    assertThat(result).isEqualTo(false);
  }

  /**
   * test to check if fireStationNumber List exist.
   * 
   */
  @Test
  public void testFireStationNumberListExist() {
    List<Integer> stationNumberList = new ArrayList<>();
    stationNumberList.add(1);
    List<Integer> FireStationNumberNotFound = new ArrayList<>();

    when(fireStationRepositoryMock.existsByStation(1)).thenReturn(true);

    List<Integer> result = fireStationService.fireStationNumberListExist(stationNumberList);
    assertThat(result).isEqualTo(FireStationNumberNotFound);
  }

}
