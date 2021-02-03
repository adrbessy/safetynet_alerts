package com.safetynet.alerts_api.service.medicalRecord;

import com.safetynet.alerts_api.model.MedicalRecord;
import java.util.List;

public interface MedicalRecordService {

  /**
   * Check if the first name and the last name exists in the medicalrecord table.
   * 
   * @param firstName
   * @param lastName
   * @return true if they exist, otherwise returns false
   */
  public boolean medicalRecordFirstNameLastNameExist(String firstName, String lastName);


  /**
   * Check if the medical record id exists in the medicalrecord table.
   * 
   * @param id
   * @return true if it exists, otherwise returns false
   */
  public boolean medicalRecordIdExist(Long id);


  /**
   * Delete a MedicalRecord
   * 
   * @param firstName
   * @param lastName
   */
  public void deleteMedicalRecord(final String firstname, final String lastname);


  /**
   * Get a MedicalRecord with a given id
   * 
   * @param id
   * @return the medicalRecord if it exists, otherwise returns null
   */
  public MedicalRecord getMedicalRecord(final Long id);


  /**
   * Save the medical record list
   * 
   * @param MedicalRecordList A list of medical records to save
   * @return true if everything goes right, otherwise returns false
   */
  public boolean saveAllMedicalRecords(List<MedicalRecord> medicalRecordList);

  /**
   * Save a medical record
   * 
   * @param medicalRecord A medical record to save
   * @return The saved medicalRecord
   */
  public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);

}
