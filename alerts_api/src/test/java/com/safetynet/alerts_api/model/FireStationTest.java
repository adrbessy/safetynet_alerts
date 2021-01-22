package com.safetynet.alerts_api.model;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest()
class FireStationTest {

  private FireStation fireStation;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @BeforeEach
  private void setUp() {
    fireStation = new FireStation();
  }

  @Test
  public void testGetId() throws Exception {
    fireStation.setId((long) 5);
    assertThat(fireStation.getId()).isEqualTo(5);
  }

  @Test
  public void testGetAddress() throws Exception {
    fireStation.setAddress("82 Alexander Road");
    assertThat(fireStation.getAddress()).isEqualTo("82 Alexander Road");
  }

  @Test
  public void testGetStation() throws Exception {
    fireStation.setStation(6);
    assertThat(fireStation.getStation()).isEqualTo(6);
  }

}
