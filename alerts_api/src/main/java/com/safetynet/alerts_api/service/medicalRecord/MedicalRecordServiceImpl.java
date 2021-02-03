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


  /**
   * Check if the first name and the last name exists in the medicalrecord table.
   * 
   * @param firstName
   * @param lastName
   * @return true if they exist, otherwise returns false
   */
  @Override
  public boolean medicalRecordFirstNameLastNameExist(String firstName, String lastName) {
    logger.debug("in the method medicalRecordFirstNameLastNameExist in the class FireStationServiceImpl");
    boolean existingMedicalRecordFirstNameLastName = medicalRecordRepository.existsByfirstNameAndLastNameAllIgnoreCase(
        firstName,
        lastName);
    return existingMedicalRecordFirstNameLastName;
  }


  /**
   * Check if the medical record id exists in the medicalrecord table.
   * 
   * @param id
   * @return true if it exists, otherwise returns false
   */
  @Override
  public boolean medicalRecordIdExist(Long id) {
    logger.debug("in the method medicalRecordIdExist in the class FireStationServiceImpl");
    boolean existingFireStationId = medicalRecordRepository.existsById(id);
    return existingFireStationId;
  }


  /**
   * Delete a MedicalRecord
   * 
   * @param firstName
   * @param lastName
   */
  @Override
  public void deleteMedicalRecord(String firstname, String lastname) {
    logger.debug("in the method deleteMedicalRecord in the class FireStationServiceImpl");
    medicalRecordRepository.deletePersonByFirstNameAndLastNameAllIgnoreCase(firstname, lastname);
  }


  /**
   * Get a MedicalRecord with a given id
   * 
   * @param id
   * @return the medicalRecord if it exists, otherwise returns null
   */
  @Override
  public MedicalRecord getMedicalRecord(final Long id) {
    logger.debug("in the method getMedicalRecord in the class FireStationServiceImpl");
    Optional<MedicalRecord> medRec = medicalRecordRepository.findById(id);
    if (medRec.isPresent()) {
      MedicalRecord medicalRecord = medRec.get();
      return medicalRecord;
    } else {
      return null;
    }
  }


  /**
   * Save a medical record
   * 
   * @param medicalRecord A medical record to save
   * @return The saved medicalRecord
   */
  @Override
  public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
    logger.debug("in the method saveMedicalRecord in the class FireStationServiceImpl");
    MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);
    return savedMedicalRecord;
  }


  /**
   * Save the medical record list
   * 
   * @param MedicalRecordList A list of medical records to save
   * @return true if everything goes right, otherwise returns false
   */
  @Override
  public boolean saveAllMedicalRecords(List<MedicalRecord> medicalRecordList) {
    logger.debug("in the method saveAllMedicalRecords in the class FireStationServiceImpl");
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
