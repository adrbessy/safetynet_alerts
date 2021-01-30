package com.safetynet.alerts_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.medicalRecord.MedicalRecordService;
import java.util.ArrayList;
import java.util.List;
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


@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MedicalRecordService medicalRecordService;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  private MedicalRecord medicalRecord;

  @BeforeEach
  private void setUp() {
    medicalRecord = new MedicalRecord();
    medicalRecord.setLastName("myLastName");
    medicalRecord.setFirstName("myFirstName");
    medicalRecord.setBirthdate("16/06/1988");

    List<String> allergiesList = new ArrayList<>();
    allergiesList.add("Allergie 1");
    allergiesList.add("Allergie 2");

    medicalRecord.setAllergies(allergiesList);

    List<String> medicationList = new ArrayList<>();
    medicationList.add("medicament 1");
    medicationList.add("medicament 2");
    medicalRecord.setMedications(medicationList);
  }

  @Test
  public void testDeleteMedicalRecord() throws Exception {
    String firstName = "Jacob";
    String lastName = "Boyd";
    when(medicalRecordService.medicalRecordFirstNameLastNameExist(firstName, lastName)).thenReturn(true);
    mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord?firstName=Jacob&lastName=Boyd"))
        .andExpect(status().isOk());
  }

  @Test
  public void testCreateMedicalRecord() throws Exception {
    when(medicalRecordService.saveMedicalRecord(medicalRecord)).thenReturn(medicalRecord);
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(medicalRecord));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }


  @Test
  public void testUpdateMedicalRecord() throws Exception {
    long id = 1;
    when(medicalRecordService.medicalRecordIdExist(id)).thenReturn(true);
    when(medicalRecordService.getMedicalRecord(id)).thenReturn(medicalRecord);
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/medicalRecord/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(medicalRecord));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

}

