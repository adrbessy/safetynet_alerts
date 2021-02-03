package com.safetynet.alerts_api.service.email;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;

  /**
   * Get the list of email address of persons from a Person List.
   * 
   * @param personList A Person List
   * @return A list of email address
   */
  @Override
  public List<String> getEmailListFromPersonList(List<Person> personList) {
    logger.debug("in the method getEmailListFromPersonList in the class EmailServiceImpl");
    List<String> emailList = new ArrayList<>();
    if (personList != null) {
      personList.forEach(personIterator -> {
        if (personIterator.getEmail() != null && !personIterator.getEmail().isEmpty()) {
          emailList.add(personIterator.getEmail());
        }
      });
    }
    LinkedHashSet<String> hashSet = new LinkedHashSet<>(emailList);
    List<String> listWithoutDuplicates = new ArrayList<>(hashSet);
    return listWithoutDuplicates;
  }

  /**
   * Read - Get the list of email address of persons living in a given city.
   * 
   * @param city A city
   * @return A list of email address
   */
  @Override
  public List<String> getPersonEmailFromCity(String city) {
    logger.debug("in the method getPersonEmailFromCity in the class EmailServiceImpl");
    List<Person> filteredPersonList = personRepository.findDistinctByCityAllIgnoreCase(city);
    List<String> emailListNoDuplicates = getEmailListFromPersonList(filteredPersonList);
    return emailListNoDuplicates;
  }

}
