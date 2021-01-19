package com.safetynet.alerts_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.service.person.PersonService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonInfoController {

  private static final Logger logger = LogManager.getLogger(PersonInfoController.class);

  @Autowired
  private PersonService personService;


  /**
   * Delete - Delete an person
   * 
   * @param id - The id of the person to delete
   */
  @Transactional
  @DeleteMapping("/person")
  public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
    logger.info("Delete request with the endpoint 'person' received with parameters firstName :" + firstName
        + " and lastName : " + lastName);
    personService.deletePerson(firstName, lastName);
    logger.info(
        "response following the Delete on the endpoint 'person' with the given firstName : {"
            + firstName + "and the given lastName : {" + lastName + "}");
  }


  /**
   * Update - Update an existing person
   * 
   * @param id     - The id of the eperson to update
   * @param person - The person object updated
   * @return
   */
  @PutMapping("/person/{id}")
  public Person updatePerson(@PathVariable("id") final Long id, @RequestBody Person person) {
    Person persToUpdate = personService.getPerson(id);
    logger.info(
        "response following the Put on the endpoint 'person' with the given id : {"
            + id.toString() + "}");
    if (persToUpdate != null) {
      String address = person.getAddress();
      if (address != null) {
        persToUpdate.setAddress(address);
      }
      String city = person.getCity();
      if (city != null) {
        persToUpdate.setCity(city);
        ;
      }
      String zip = person.getZip();
      if (zip != null) {
        persToUpdate.setZip(zip);
      }
      String phone = person.getPhone();
      if (phone != null) {
        persToUpdate.setPhone(phone);
        ;
      }
      String email = person.getEmail();
      if (email != null) {
        persToUpdate.setEmail(email);
        ;
      }
      personService.savePerson(persToUpdate);
      return persToUpdate;
    } else {
      return null;
    }
  }


  /**
   * Create - Add a new person
   * 
   * @param person An object person
   * @return The person object saved
   */
  @PostMapping("/person")
  public Person createPerson(@RequestBody Person person) {
    Person newPerson = personService.savePerson(person);
    logger.info(
        "response following the Post on the endpoint 'person' with the given person : {"
            + person.toString() + "}");
    return newPerson;
  }


  /**
   * Read - Get the information about a person from his first name and his last
   * name; get also the information about the persons having the same last name.
   * 
   * @param a List of number of fire station
   * @return - A List of Person
   */
  @GetMapping("/personInfo")
  public String getPersonInfoFromFirstNameAndLastName(@RequestParam String firstName,
      @RequestParam String lastName) {
    logger.info(
        "Get request of the endpoint 'personInfo' with the first name : {" + firstName + "} and the last name : "
            + lastName);
    List<Person> personInfoByaddressList = personService.getPersonListByFirstNameAndLastNameThenOnlyLastName(firstName,
        lastName);
    logger.info(
        "response following the Get on the endpoint 'personInfo' with the given address :{" + firstName
            + "} and the last name :{"
            + lastName + "}");
    ObjectMapper mapper = new ObjectMapper();
    try {
      String normalView = mapper.writerWithView(PersonInfoController.class)
          .writeValueAsString(personInfoByaddressList);
      return normalView;
    } catch (JsonProcessingException e) {
      logger.error("Unable to process the filter in getPersonInfoFromFirstNameAndLastName: ", e);
      return null;
    }
  }

}
