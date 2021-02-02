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
  public boolean personFirstNameLastNameExist(String firstName, String lastName) {
    logger.debug("in the method personFirstNameLastNameExist in the class PersonServiceImpl");
    boolean existingPersonId = personRepository.existsByfirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    return existingPersonId;
  }


  @Override
  public boolean personIdExist(Long id) {
    logger.debug("in the method personIdExist in the class PersonServiceImpl");
    boolean existingPersonId = personRepository.existsById(id);
    return existingPersonId;
  }


  @Override
  public boolean personAddressExist(String address) {
    logger.debug("in the method personAddressExist in the class PersonServiceImpl");
    boolean existingPersonAddress = personRepository.existsByAddress(address);
    return existingPersonAddress;
  }


  @Override
  public boolean cityExist(String city) {
    logger.debug("in the method cityExist in the class PersonServiceImpl");
    boolean existingCity = personRepository.existsByCityIgnoreCase(city);
    return existingCity;
  }


  @Override
  public void deletePerson(String firstName, String lastName) {
    logger.debug("in the method deletePerson in the class PersonServiceImpl");
    personRepository.deletePersonByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
  }


  @Override
  public Person getPerson(final Long id) {
    logger.debug("in the method getPerson in the class PersonServiceImpl");
    Person personToUpdate = null;
    try {
      Optional<Person> pers = personRepository.findById(id);
      if (pers.isPresent()) {
        personToUpdate = pers.get();
      } else {
        return null;
      }
    } catch (Exception exception) {
      logger.error("Error when we try to get a person :" + exception.getMessage());
    }
    return personToUpdate;
  }

  @Override
  public Person savePerson(Person person) {
    logger.debug("in the method savePerson in the class PersonServiceImpl");
    Person savedPerson = null;
    try {
      savedPerson = personRepository.save(person);
    } catch (Exception exception) {
      logger.error("Error when we try to save a person :" + exception.getMessage());
    }
    return savedPerson;
  }


  @Override
  public boolean saveAllPersons(List<Person> personList) {
    logger.debug("in the method saveAllPersons in the class PersonServiceImpl");
    if (personList != null) {
      try {
        personRepository.saveAll(personList);
        return true;
      } catch (Exception exception) {
        logger.error("Erreur lors de l'enregistrement de la liste des personnes " + exception.getMessage());
      }
    }
    return false;
  }

}
