package com.safetynet.alerts_api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.person.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CommunityCoveredByFireStationController.class)
public class CommunityCoveredByFireStationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @Test
  public void testGetPersonListCoveredByThisStation() throws Exception {
    mockMvc.perform(get("/firestation?stationNumber=1"))
        .andExpect(status().isOk());
  }

}
