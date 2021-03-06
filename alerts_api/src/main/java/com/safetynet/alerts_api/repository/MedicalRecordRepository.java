package com.safetynet.alerts_api.repository;

import com.safetynet.alerts_api.model.MedicalRecord;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

  List<MedicalRecord> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

  void deletePersonByFirstNameAndLastNameAllIgnoreCase(String firstname, String lastname);

  List<MedicalRecord> getMedicalRecordByFirstNameAndLastName(String firstname, String lastname);

  boolean existsByfirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

}
