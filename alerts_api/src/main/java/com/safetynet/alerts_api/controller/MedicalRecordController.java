package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.service.medicalRecord.MedicalRecordService;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
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
        + " and lastName : " + lastName);
    medicalRecordService.deleteMedicalRecord(firstName, lastName);
  }


  /**
   * Create - Add a new medical record
   * 
   * @param medical record An object medical record
   * @return The medical record object saved
   */
  @PostMapping("/medicalRecord")
  public MappingJacksonValue createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    MedicalRecord savedMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "firstName", "lastName",
        "address",
        "phone");
    FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
    MappingJacksonValue filteredMedicalRecordList = new MappingJacksonValue(savedMedicalRecord);
    filteredMedicalRecordList.setFilters(filterList);
    return filteredMedicalRecordList;
  }


  /**
   * Update - Update an existing medical record
   * 
   * @param id     - The id of the medical record to update
   * @param person - The medical record object updated
   * @return
   */
  @PutMapping("/medicalRecord/{id}")
  public MappingJacksonValue updateMedicalRecord(@PathVariable("id") final Long id,
      @RequestBody MedicalRecord medicalRecord) {
    Optional<MedicalRecord> e = medicalRecordService.getMedicalRecord(id);
    if (e.isPresent()) {
      MedicalRecord currentMedicalRecord = e.get();

      String birthdate = medicalRecord.getBirthdate();
      if (birthdate != null) {
        currentMedicalRecord.setBirthdate(birthdate);
      }
      List<String> medications = medicalRecord.getMedications();
      if (medications != null) {
        currentMedicalRecord.setMedications(medications);
      }
      List<String> allergies = medicalRecord.getAllergies();
      if (allergies != null) {
        currentMedicalRecord.setAllergies(allergies);
      }
      medicalRecordService.saveMedicalRecord(currentMedicalRecord);
      SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "firstName", "lastName",
          "birthdate", "medications", "allergies");
      FilterProvider filterList = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
      MappingJacksonValue filteredFireStationList = new MappingJacksonValue(currentMedicalRecord);
      filteredFireStationList.setFilters(filterList);
      return filteredFireStationList;
    } else {
      return null;
    }
  }


}
