package com.safetynet.alerts_api.service;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private static final Logger logger = LogManager.getLogger(PersonService.class);

  @Autowired
  private PersonRepository personRepository;

  /**
   * Sauvegarde la liste des personnes passées en paramètre
   *
   * @param personList Liste des personnes à sauvegarder
   */
  public boolean saveAllPersons(List<Person> personList) {

    if (personList != null) {
      try {
        personRepository.saveAll(personList);
        return true;
      } catch (Exception exception) {
        logger.error("Erreur lors de l'enregistrement de la liste des personnes " + exception.getMessage()
            + " , Stack Trace : " + exception.getStackTrace());
      }
    }
    return false;
  }

}
