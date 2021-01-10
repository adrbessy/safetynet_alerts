package com.safetynet.alerts_api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.phone.PhoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CommunityPhoneController.class)
class CommunityPhoneControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PhoneService phoneService;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @Test
  public void testGetPhoneNumberCoveredByThisStation() throws Exception {
    mockMvc.perform(get("/phoneAlert?firestation=3"))
        .andExpect(status().isOk());
  }

}
