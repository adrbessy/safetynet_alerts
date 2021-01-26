package com.safetynet.alerts_api.service.person;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
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
  public void deletePerson(String firstName, String lastName) {
    try {
      personRepository.deletePersonByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    } catch (Exception exception) {
      logger.error("Error when we try to delete a person :" + exception.getMessage());
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
