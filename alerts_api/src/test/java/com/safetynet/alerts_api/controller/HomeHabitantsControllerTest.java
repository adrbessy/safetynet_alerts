package com.safetynet.alerts_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.childAlert.ChildAlertService;
import com.safetynet.alerts_api.service.fire.FireService;
import com.safetynet.alerts_api.service.person.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HomeHabitantsController.class)
public class HomeHabitantsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personServiceMock;

  @MockBean
  private FireService fireServiceMock;

  @MockBean
  private ChildAlertService childAlertServiceMock;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  @Test
  public void testGetFire() throws Exception {
    String address = "1509 Culver St";
    when(personServiceMock.personAddressExist(address)).thenReturn(true);
    mockMvc.perform(get("/fire?address=" + address))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetFireIfAddressDoesntExist() throws Exception {
    String address = "1509 Culver S";
    when(personServiceMock.personAddressExist(address)).thenReturn(false);
    mockMvc.perform(get("/fire?address=" + address))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetHome() throws Exception {
    String address = "1509 Culver St";
    when(personServiceMock.personAddressExist(address)).thenReturn(true);
    mockMvc.perform(get("/childAlert?address=" + address))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetHomeIfAddressDoesntExist() throws Exception {
    String address = "1509 Culver S";
    when(personServiceMock.personAddressExist(address)).thenReturn(false);
    mockMvc.perform(get("/childAlert?address=" + address))
        .andExpect(status().isNotFound());
  }

}
