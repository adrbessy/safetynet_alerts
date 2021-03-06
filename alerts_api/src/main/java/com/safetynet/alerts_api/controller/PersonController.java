package com.safetynet.alerts_api.controller;

import com.safetynet.alerts_api.exceptions.NonexistentException;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfoDTO;
import com.safetynet.alerts_api.service.email.EmailService;
import com.safetynet.alerts_api.service.person.PersonService;
import com.safetynet.alerts_api.service.personInfo.PersonInfoService;
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
public class PersonController {

  private static final Logger logger = LogManager.getLogger(PersonController.class);

  @Autowired
  private PersonService personService;

  @Autowired
  private PersonInfoService personInfoService;

  @Autowired
  private EmailService emailService;


  /**
   * Add a new person
   * 
   * @param person An object person
   * @return The person object saved
   */
  @PostMapping("/person")
  public Person createPerson(@RequestBody Person person) {
    Person newPerson = null;
    try {
      newPerson = personService.savePerson(person);
      logger.info(
          "response following the Post on the endpoint 'person' with the given person : {"
              + person.toString() + "}");
    } catch (Exception exception) {
      logger.error("Error in the PersonInfoController in the method createPerson :"
          + exception.getMessage());
    }
    return newPerson;
  }


  /**
   * Delete a person from a given first name and a given last name
   * 
   * @param firstName The given first name
   * @param lastName  The given last name
   */
  @Transactional
  @DeleteMapping("/person")
  public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
    boolean existingPersonFirstNameLastName = false;
    try {
      logger.info("Delete request with the endpoint 'person' received with parameters firstName :" + firstName
          + " and lastName : " + lastName);
      existingPersonFirstNameLastName = personService.personFirstNameLastNameExist(firstName, lastName);
      if (existingPersonFirstNameLastName) {
        personService.deletePerson(firstName, lastName);
        logger.info(
            "response following the Delete on the endpoint 'person' with the given firstName : {"
                + firstName + "and the given lastName : {" + lastName + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the PersonInfoController in the method deletePerson :"
          + exception.getMessage());
    }
    if (!existingPersonFirstNameLastName) {
      logger.error("The person with the first name " + firstName + " and last name " + lastName + " doesn't exist.");
      throw new NonexistentException(
          "The person with the first name " + firstName + " and last name " + lastName + " doesn't exist.");
    }
  }


  /**
   * Update an existing person from a given id
   * 
   * @param id     The given id
   * @param person The updated person object
   * @return The updated person object
   */
  @PutMapping("/person/{id}")
  public Person updatePerson(@PathVariable("id") final Long id, @RequestBody Person person) {
    Person persToUpdate = null;
    boolean existingPersonId = false;
    try {
      logger.info(
          "Put request of the endpoint 'person' with the id : {" + id + "}");
      existingPersonId = personService.personIdExist(id);
      if (existingPersonId) {
        persToUpdate = personService.getPerson(id);
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
          }
          personService.savePerson(persToUpdate);
        }
      }
    } catch (Exception exception) {
      logger.error("Error in the PersonInfoController in the method updatePerson :"
          + exception.getMessage());
    }
    if (!existingPersonId) {
      logger.error("The person with the id " + id + " doesn't exist.");
      throw new NonexistentException(
          "The person with the id " + id + " doesn't exist.");
    }
    return persToUpdate;
  }


  /**
   * Get the information about a person from his first name and his last name; get
   * also the information about the persons having the same last name.
   * 
   * @param firstName The given first name
   * @param lastName  The given last name
   * @return A List of Person
   */
  @GetMapping("/personInfo")
  public List<PersonInfoDTO> getPersonInfo(@RequestParam String firstName,
      @RequestParam String lastName) {
    List<PersonInfoDTO> personInfoByaddressList = null;
    boolean existingPersonFistNameLastName = false;
    try {
      logger.info(
          "Get request of the endpoint 'personInfo' with the first name : {" + firstName + "} and the last name : "
              + lastName);
      existingPersonFistNameLastName = personService.personFirstNameLastNameExist(firstName, lastName);
      if (existingPersonFistNameLastName) {
        personInfoByaddressList = personInfoService
            .getPersonListByFirstNameAndLastNameThenOnlyLastName(
                firstName,
                lastName);
        logger.info(
            "response following the Get on the endpoint 'personInfo' with the given address :{" + firstName
                + "} and the last name :{"
                + lastName + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the PersonInfoController in the method getPersonInfo :"
          + exception.getMessage());
    }
    if (!existingPersonFistNameLastName) {
      logger.error("The person with the the first name : " + firstName + " and the last name : " + lastName
          + " doesn't exist.");
      throw new NonexistentException(
          "The person with the the first name : " + firstName + " and the last name : " + lastName
              + " doesn't exist.");
    }
    return personInfoByaddressList;
  }


  /**
   * Get the list of all email of persons living in a given city.
   * 
   * @param city The given city
   * @return A List of email
   */
  @GetMapping("/communityEmail")
  public List<String> getCommunityEmail(@RequestParam String city) {
    List<String> emailList = null;
    boolean cityExist = false;
    try {
      logger.info(
          "Get request of the endpoint 'communityEmail' with the city : {" + city + "}");
      cityExist = personService.cityExist(city);
      if (cityExist) {
        emailList = emailService.getPersonEmailFromCity(city);
        logger.info("response following the Get on the endpoint 'communityEmail' with the given city : {" + city + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the PersonInfoController in the method getCommunityEmail :"
          + exception.getMessage());
    }
    if (!cityExist) {
      logger.error("The city : " + city + " doesn't exist.");
      throw new NonexistentException(
          "The city : " + city + " doesn't exist.");
    }
    return emailList;
  }

}
