package com.safetynet.alerts_api.service.home;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.personInfo.PersonInfoService;
import java.time.LocalDate;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

  private static final Logger logger = LogManager.getLogger(HomeServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private PersonInfoService personInfoService;


  /**
   * Get a list of persons by address from a given address.
   * 
   * @param address An address
   * @return A List of Person
   */
  @Override
  public List<Person> getPersonListByAddress(String address) {
    logger.debug("in the method getPersonListByAddress in the class HomeServiceImpl");
    List<Person> filteredPersonList = null;
    try {
      // we retrieve the list of persons corresponding to the address
      filteredPersonList = personRepository.findDistinctByAddress(address);
      personInfoService.setAgeAndMedicationsAndAllergiesFromPersonList(filteredPersonList, LocalDate.now());
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonList By address :"
          + exception.getMessage());
    }
    return filteredPersonList;
  }

}
