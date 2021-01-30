package com.safetynet.alerts_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.community.CommunityService;
import com.safetynet.alerts_api.service.person.PersonService;
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
  private PersonService personService;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @Test
  public void testGetFire() throws Exception {
    String address = "1509 Culver St";
    when(personService.personAddressExist(address)).thenReturn(true);
    mockMvc.perform(get("/fire?address=" + address))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetHome() throws Exception {
    String address = "1509 Culver St";
    when(personService.personAddressExist(address)).thenReturn(true);
    mockMvc.perform(get("/childAlert?address=" + address))
        .andExpect(status().isOk());
  }

}
