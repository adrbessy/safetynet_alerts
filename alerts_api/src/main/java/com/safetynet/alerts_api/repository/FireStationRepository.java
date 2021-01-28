package com.safetynet.alerts_api.repository;

import com.safetynet.alerts_api.model.FireStation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends CrudRepository<FireStation, Long> {

  List<FireStation> findDistinctByStation(int stationNumber);

  List<FireStation> findDistinctByAddress(String address);

  Optional<FireStation> findByAddress(String address);

  List<FireStation> findDistinctByAddressIgnoreCase(String address);

  void deleteByAddress(String address);

  boolean existsByStation(Integer stationNumber);

  boolean existsByAddress(String address);

}
