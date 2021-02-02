package com.safetynet.alerts_api.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.service.medicalRecord.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest()
public class MedicalRecordServiceTest {

  @Autowired
  private MedicalRecordService medicalRecordService;

  @MockBean
  private MedicalRecordRepository medicalRecordRepositoryMock;

  @BeforeEach
  private void setUp() {

  }

  /**
   * test to delete a medical record.
   * 
   */
  @Test
  public void testDeleteMedicalRecord() {
    doNothing().when(medicalRecordRepositoryMock).deletePersonByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy");
    medicalRecordService.deleteMedicalRecord("Adrien", "Bessy");
    verify(medicalRecordRepositoryMock, Mockito.times(1)).deletePersonByFirstNameAndLastNameAllIgnoreCase("Adrien",
        "Bessy");
  }

}
