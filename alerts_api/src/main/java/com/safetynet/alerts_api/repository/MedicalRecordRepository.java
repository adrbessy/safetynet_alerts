package com.safetynet.alerts_api.repository;

import com.safetynet.alerts_api.model.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

}
