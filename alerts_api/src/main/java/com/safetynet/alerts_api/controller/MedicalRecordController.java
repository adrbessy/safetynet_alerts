package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.exceptions.NonexistentException;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.service.medicalRecord.MedicalRecordService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {

  private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

  @Autowired
  private MedicalRecordService medicalRecordService;


  /**
   * Delete a medical record from a given first name and a given last name
   * 
   * @param firstName The given first name
   * @param lastName  The given last name
   */
  @Transactional
  @DeleteMapping("/medicalRecord")
  public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
    boolean existingMedicalRecord = false;
    try {
      logger.info("Delete request with the endpoint 'medicalRecord' received with parameters firstName :" + firstName
          + " and the given lastName : " + lastName);
      existingMedicalRecord = medicalRecordService.medicalRecordFirstNameLastNameExist(firstName, lastName);
      if (existingMedicalRecord) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        logger.info(
            "response following the Delete on the endpoint 'medicalRecord' with the given firstName : {"
                + firstName + "and the given lastName : {" + lastName + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the MedicalRecordController in the method deleteMedicalRecord :"
          + exception.getMessage());
    }
    if (!existingMedicalRecord) {
      logger.error(
          "The medicalRecord with the first name " + firstName + " and last name " + lastName + " doesn't exist.");
      throw new NonexistentException(
          "The medicalRecord with the first name " + firstName + " and last name " + lastName + " doesn't exist.");
    }
  }


  /**
   * Add a new medical record
   * 
   * @param medicalRecord An new object medical record
   * @return The saved medical record object
   */
  @PostMapping("/medicalRecord")
  public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    MedicalRecord savedMedicalRecord = null;
    try {
      logger.info("Post request with the endpoint 'medicalRecord' received with the medicalRecord :"
          + medicalRecord.toString());
      savedMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
      logger.info(
          "response following the Post on the endpoint 'medicalRecord' with the given fireStation : {"
              + medicalRecord.toString() + "}");
    } catch (Exception exception) {
      logger.error("Error in the MedicalRecordController in the method createMedicalRecord :"
          + exception.getMessage());
    }
    return savedMedicalRecord;
  }


  /**
   * Update - Update an existing medical record
   * 
   * @param id            The id of the medical record to update
   * @param medicalRecord The updated medical record object
   * @return The updated MedicalRecord
   */
  @PutMapping("/medicalRecord/{id}")
  public MedicalRecord updateMedicalRecord(@PathVariable("id") final Long id,
      @RequestBody MedicalRecord medicalRecord) {
    boolean existingMedicalRecordId = false;
    MedicalRecord medicalRecordToUpdate = null;
    try {
      logger.info("Put request with the endpoint 'medicalRecord' received with the medicalRecord Id:"
          + id.toString());
      existingMedicalRecordId = medicalRecordService.medicalRecordIdExist(id);
      if (existingMedicalRecordId) {
        medicalRecordToUpdate = medicalRecordService.getMedicalRecord(id);
        logger.info(
            "response following the Put on the endpoint 'medicalRecord' with the given id : {"
                + id.toString() + "}");
        if (medicalRecordToUpdate != null) {
          String birthdate = medicalRecord.getBirthdate();
          if (birthdate != null) {
            medicalRecordToUpdate.setBirthdate(birthdate);
          }
          List<String> medications = medicalRecord.getMedications();
          if (medications != null) {
            medicalRecordToUpdate.setMedications(medications);
          }
          List<String> allergies = medicalRecord.getAllergies();
          if (allergies != null) {
            medicalRecordToUpdate.setAllergies(allergies);
          }
          medicalRecordService.saveMedicalRecord(medicalRecordToUpdate);
        }
      }
    } catch (Exception exception) {
      logger.error("Error in the MedicalRecordController in the method updateMedicalRecord :"
          + exception.getMessage());
    }
    if (!existingMedicalRecordId) {
      logger.error("The medical record with the id " + id.toString() + " doesn't exist.");
      throw new NonexistentException(
          "The medical record with the id " + id.toString() + " doesn't exist.");
    }
    return medicalRecordToUpdate;
  }


}
