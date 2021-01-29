package com.safetynet.alerts_api.repository;

import com.safetynet.alerts_api.model.Person;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

  List<Person> findAllByAddressInOrderByAddress(List<String> addressList);

  List<Person> findDistinctByAddress(String address);

  List<Person> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

  List<Person> findByLastNameAllIgnoreCase(String lastName);

  List<Person> findDistinctByCity(String city);

  List<Person> findDistinctByCityAllIgnoreCase(String city);

  void deleteByFirstNameAndLastName(String firstName, String lastName);

  void deletePersonByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

  void deleteByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

  boolean existsByCity(String city);

  boolean existsByCityIgnoreCase(String city);

  boolean existsByAddress(String address);

  boolean existsByfirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

}
