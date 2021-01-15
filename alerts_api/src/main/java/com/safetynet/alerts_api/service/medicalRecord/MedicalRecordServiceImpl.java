package com.safetynet.alerts_api.service.medicalRecord;

import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.service.fireStation.FireStationServiceImpl;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

  private static final Logger logger = LogManager.getLogger(FireStationServiceImpl.class);

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;


  @Override
  public void deleteMedicalRecord(final String firstname, final String lastname) {
    try {
      medicalRecordRepository.deletePersonByFirstNameAndLastNameAllIgnoreCase(firstname, lastname);

    } catch (Exception exception) {
      logger.error("Error when we try to delete a medical record:" + exception.getMessage());
    }
  }



  @Override
  public Optional<MedicalRecord> getMedicalRecord(final Long id) {
    return medicalRecordRepository.findById(id);
  }


  @Override
  public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
    MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);
    return savedMedicalRecord;
  }


  @Override
  public boolean saveAllMedicalRecords(List<MedicalRecord> medicalRecordList) {
    if (medicalRecordList != null && !medicalRecordList.isEmpty()) {
      try {
        medicalRecordRepository.saveAll(medicalRecordList);
        return true;
      } catch (Exception exception) {
        logger.error("Erreur lors de l'enregistrement de la liste des données médicales " + exception.getMessage());
      }
    }
    return false;
  }

}
