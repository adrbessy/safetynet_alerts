package com.safetynet.alerts_api.service.medicalRecord;

import com.safetynet.alerts_api.model.MedicalRecord;
import java.util.List;
import java.util.Optional;

public interface MedicalRecordService {

  /**
   * Delete a MedicalRecord
   * 
   * @param first name and last name
   * 
   */
  public void deleteMedicalRecord(final String firstname, final String lastname);


  /**
   * Get a MedicalRecord
   * 
   * @param MedicalRecord to get
   * @return the medicalRecord
   * 
   */
  public Optional<MedicalRecord> getMedicalRecord(final Long id);


  /**
   * Save the medical record list
   * 
   * @param MedicalRecordList to save
   * @return true if everything goes right, otherwise returns false
   * 
   */
  public boolean saveAllMedicalRecords(List<MedicalRecord> medicalRecordList);

  /**
   * Save a medical record
   * 
   * @param medicalRecord to save
   * @return the saved medicalRecord
   * 
   */
  public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);

}
