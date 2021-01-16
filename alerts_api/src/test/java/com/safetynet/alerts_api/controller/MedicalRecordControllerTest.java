package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.medicalRecord.MedicalRecordService;
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

  @Test
  public void testUpdateMedicalRecord() throws Exception {
    long id = 1;
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/medicalRecord/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(getMedicalRecordInJson(1));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());

  }

  private String getMedicalRecordInJson(long id) {
    return "{\"id\":" + id + ", \"birthdate\":\"16/06/1988\"}";
  }

}

