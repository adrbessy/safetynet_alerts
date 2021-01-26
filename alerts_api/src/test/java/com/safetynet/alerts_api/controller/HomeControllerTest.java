package com.safetynet.alerts_api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.community.CommunityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CommunityService communityService;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @Test
  public void testGetPersonListLivingToThisAdressAndFirestationNumber() throws Exception {
    mockMvc.perform(get("/fire?address=112%20Steppes%20Pl"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetChildListLivingToThisAdress() throws Exception {
    mockMvc.perform(get("/childAlert?address=1509%20Culver%20St"))
        .andExpect(status().isOk());
  }

}
