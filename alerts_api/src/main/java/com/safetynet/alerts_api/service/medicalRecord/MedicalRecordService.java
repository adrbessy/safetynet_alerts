package com.safetynet.alerts_api.service.medicalRecord;

import com.safetynet.alerts_api.model.MedicalRecord;
import java.util.List;

public interface MedicalRecordService {

  /**
   * Check if the first name and the last name exists in the medicalrecord table.
   * 
   * @param firestation id
   * @return - A boolean
   */
  public boolean medicalRecordFirstNameLastNameExist(String firstName, String lastName);


  /**
   * Check if all the fire station number exists in the medicalrecord table.
   * 
   * @param firestation id
   * @return - A boolean
   */
  public boolean medicalRecordIdExist(Long id);


  /**
   * Delete a MedicalRecord
   * 
   * @param first name and last name
   * @return boolean
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
  public MedicalRecord getMedicalRecord(final Long id);


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
