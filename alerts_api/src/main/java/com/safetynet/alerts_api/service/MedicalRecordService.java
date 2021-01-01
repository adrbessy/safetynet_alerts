package com.safetynet.alerts_api.service;

import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

  private static final Logger logger = LogManager.getLogger(FireStationService.class);

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  /**
   * Sauvergarde la liste des données médicales
   *
   * @param medicalRecordList liste des données médicales à sauvegarder
   */
  public boolean saveAllMedicalRecords(List<MedicalRecord> medicalRecordList) {
    if (medicalRecordList != null && !medicalRecordList.isEmpty()) {
      try {
        medicalRecordRepository.saveAll(medicalRecordList);
        return true;
      } catch (Exception exception) {
        logger.error("Erreur lors de l'enregistrement de la liste des données médicales " + exception.getMessage()
            + " , Stack Trace : " + exception.getStackTrace());
      }
    }
    return false;
  }

}
