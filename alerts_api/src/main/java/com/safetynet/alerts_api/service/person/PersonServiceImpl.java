package com.safetynet.alerts_api.service.person;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

  private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;


  @Override
  public boolean personFistNameLastNameExist(String firstName, String lastName) {
    boolean existingPersonId = personRepository.existsByfirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    return existingPersonId;
  }


  @Override
  public boolean personIdExist(Long id) {
    boolean existingPersonId = personRepository.existsById(id);
    return existingPersonId;
  }


  @Override
  public boolean personAddressExist(String address) {
    boolean existingPersonAddress = personRepository.existsByAddress(address);
    return existingPersonAddress;
  }


  @Override
  public boolean cityExist(String city) {
    boolean existingCity = personRepository.existsByCityIgnoreCase(city);
    return existingCity;
  }


  @Override
  public boolean deletePerson(String firstName, String lastName) {
    List<Person> existingPersonList = personRepository
        .findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    if (existingPersonList.isEmpty()) {
      return false;
    } else {
      personRepository.deletePersonByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
      return true;
    }
  }

  @Override
  public Person getPerson(final Long id) {
    try {
      Optional<Person> pers = personRepository.findById(id);
      if (pers.isPresent()) {
        Person personToUpdate = pers.get();
        return personToUpdate;
      } else {
        return null;
      }
    } catch (Exception exception) {
      logger.error("Error when we try to get a person :" + exception.getMessage());
      return null;
    }
  }

  @Override
  public Person savePerson(Person person) {
    try {
      Person savedPerson = personRepository.save(person);
      return savedPerson;
    } catch (Exception exception) {
      logger.error("Error when we try to save a person :" + exception.getMessage());
      return null;
    }
  }

}
