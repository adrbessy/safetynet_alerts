package com.safetynet.alerts_api.service;

import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.service.medicalRecord.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class MedicalRecordServiceTest {

  private MedicalRecordServiceImpl medicalRecordService;

  @Mock
  private MedicalRecordRepository medicalRecordRepositoryMock;

  @BeforeEach
  private void setUp() {
    medicalRecordService = new MedicalRecordServiceImpl();
  }

  /**
   * test to delete a medical record.
   * 
   */
  @Test
  public void testDeleteMedicalRecord() {
    /*
     * Mockito.doThrow(new Exception()).when(medicalRecordRepositoryMock).
     * deletePersonByFirstNameAndLastNameAllIgnoreCase( any(String.class),
     * any(String.class)); medicalRecordService.deleteMedicalRecord("Adrien",
     * "Bessy"); verify(medicalRecordRepositoryMock,
     * Mockito.times(1)).deletePersonByFirstNameAndLastNameAllIgnoreCase(
     * any(String.class), any(String.class));
     */
  }

}
