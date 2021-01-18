package com.safetynet.alerts_api.controller;

import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.fireStation.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FireStationService fireStationService;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  private FireStation fireStation;


  @BeforeEach
  private void setUp() {
    fireStation = new FireStation();
    fireStation.setAddress("1 rue antonio vivaldi");
    fireStation.setStation(4);
  }


  @Test
  public void testCreateFireStation() throws Exception {
    when(fireStationService.saveFireStation(fireStation)).thenReturn(fireStation);
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/firestation")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(fireStation));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }


  @Test
  public void testUpdateFireStation() throws Exception {
    long id = 1;
    when(fireStationService.getFireStation(id)).thenReturn(fireStation);
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/firestation/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(fireStation));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());

  }

}
