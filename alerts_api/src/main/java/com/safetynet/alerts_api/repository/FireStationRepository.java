package com.safetynet.alerts_api.repository;

import com.safetynet.alerts_api.model.FireStation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends CrudRepository<FireStation, Long> {

}
