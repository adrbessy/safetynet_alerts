package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.service.medicalRecord.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {

  @Autowired
  private MedicalRecordService medicalRecordService;

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

}
