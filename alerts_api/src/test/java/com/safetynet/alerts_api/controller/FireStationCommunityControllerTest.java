package com.safetynet.alerts_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.community.CommunityService;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import com.safetynet.alerts_api.service.phone.PhoneService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FireStationCommunityController.class)
public class FireStationCommunityControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CommunityService communityService;

  @MockBean
  private PhoneService phoneService;

  @MockBean
  private FireStationService fireStationServiceMock;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @Test
  public void testGetFireStationCommunity() throws Exception {
    when(fireStationServiceMock.fireStationNumberExist(1))
        .thenReturn(true);
    mockMvc.perform(get("/firestation?stationNumber=1"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetFireStationCommunityIfTheStationNumberDoesntExist() throws Exception {
    when(fireStationServiceMock.fireStationNumberExist(454))
        .thenReturn(false);
    mockMvc.perform(get("/firestation?stationNumber=454"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetPhoneNumberCoveredByThisStation() throws Exception {
    when(fireStationServiceMock.fireStationNumberExist(3))
        .thenReturn(true);
    mockMvc.perform(get("/phoneAlert?firestation=3"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetPhoneNumberCoveredByThisStationIfStationNumberDoesntExist() throws Exception {
    when(fireStationServiceMock.fireStationNumberExist(78))
        .thenReturn(false);
    mockMvc.perform(get("/phoneAlert?firestation=3"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetHomesCoveredByTheseStation() throws Exception {
    List<Integer> fireStationNumberList = new ArrayList<>();
    fireStationNumberList.add(1);
    fireStationNumberList.add(2);
    List<Integer> emptyFireStationNumberList = new ArrayList<>();
    when(fireStationServiceMock.fireStationNumberListExist(fireStationNumberList))
        .thenReturn(emptyFireStationNumberList);
    mockMvc.perform(get("/flood?stations=1,2"))
        .andExpect(status().isOk());
  }

  /*
   * @Test public void testGetHomesCoveredByTheseStationIfStationNumberNotFound()
   * throws Exception { List<Integer> fireStationNumberList = new ArrayList<>();
   * fireStationNumberList.add(1); fireStationNumberList.add(2); List<Integer>
   * fireStationNumberList2 = new ArrayList<>(); fireStationNumberList2.add(1);
   * when(fireStationServiceMock.fireStationNumberListExist(fireStationNumberList)
   * ).thenReturn(fireStationNumberList2);
   * mockMvc.perform(get("/flood?stations=1,9"))
   * .andExpect(status().isNotFound()); }
   */

}
