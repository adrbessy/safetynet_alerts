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
  public boolean medicalRecordFirstNameLastNameExist(String firstName, String lastName) {
    boolean existingMedicalRecordFirstNameLastName = medicalRecordRepository.existsByfirstNameAndLastNameAllIgnoreCase(
        firstName,
        lastName);
    return existingMedicalRecordFirstNameLastName;
  }


  @Override
  public boolean medicalRecordIdExist(Long id) {
    boolean existingFireStationId = medicalRecordRepository.existsById(id);
    return existingFireStationId;
  }


  @Override
  public void deleteMedicalRecord(String firstname, String lastname) {
    medicalRecordRepository.deletePersonByFirstNameAndLastNameAllIgnoreCase(firstname, lastname);
  }


  @Override
  public MedicalRecord getMedicalRecord(final Long id) {
    Optional<MedicalRecord> medRec = medicalRecordRepository.findById(id);
    if (medRec.isPresent()) {
      MedicalRecord medicalRecordToUpdate = medRec.get();
      return medicalRecordToUpdate;
    } else {
      return null;
    }
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
