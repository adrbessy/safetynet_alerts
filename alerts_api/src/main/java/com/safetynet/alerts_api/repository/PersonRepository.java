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

}
