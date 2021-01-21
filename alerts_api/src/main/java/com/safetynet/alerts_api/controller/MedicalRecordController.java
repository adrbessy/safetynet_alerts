package com.safetynet.alerts_api.controller;

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


  @Transactional
  @DeleteMapping("/medicalRecord")
  public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
    logger.info("Delete request with the endpoint 'medicalRecord' received with parameters firstName :" + firstName
        + " and the given lastName : " + lastName);
    medicalRecordService.deleteMedicalRecord(firstName, lastName);
    logger.info(
        "response following the Delete on the endpoint 'medicalRecord' with the given firstName : {"
            + firstName + "and the given lastName : {" + lastName + "}");
  }


  /**
   * Create - Add a new medical record
   * 
   * @param medical record An object medical record
   * @return The medical record object saved
   */
  @PostMapping("/medicalRecord")
  public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    logger.info("Post request with the endpoint 'medicalRecord' received with the medicalRecord :"
        + medicalRecord.toString());
    MedicalRecord savedMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
    logger.info(
        "response following the Post on the endpoint 'medicalRecord' with the given fireStation : {"
            + medicalRecord.toString() + "}");
    return savedMedicalRecord;
  }


  /**
   * Update - Update an existing medical record
   * 
   * @param id     - The id of the medical record to update
   * @param person - The medical record object updated
   * @return
   */
  @PutMapping("/medicalRecord/{id}")
  public MedicalRecord updateMedicalRecord(@PathVariable("id") final Long id,
      @RequestBody MedicalRecord medicalRecord) {
    logger.info("Put request with the endpoint 'medicalRecord' received with the medicalRecord Id:"
        + id.toString());
    MedicalRecord medicalRecordToUpdate = medicalRecordService.getMedicalRecord(id);
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
      return medicalRecordToUpdate;
    } else {
      return null;
    }
  }


}
