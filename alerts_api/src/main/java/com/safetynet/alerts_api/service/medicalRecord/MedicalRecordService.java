package com.safetynet.alerts_api.service.medicalRecord;

import com.safetynet.alerts_api.model.MedicalRecord;
import java.util.List;

public interface MedicalRecordService {

  /**
   * Save the medical record list
   * 
   * @param MedicalRecordList to save
   * @return true if everything goes right, otherwise returns false
   * 
   */
  public boolean saveAllMedicalRecords(List<MedicalRecord> medicalRecordList);

}
