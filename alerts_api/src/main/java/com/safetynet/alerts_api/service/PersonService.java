package com.safetynet.alerts_api.service;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.model.PersonInfo;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

  @Autowired
  private FireStationRepository firestationRepository;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

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

  public List<Home> getChildrenList(String address) {

    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);

    // we retrieve the list of children from the list of persons
    List<Person> childrenList = new ArrayList<>();
    List<Person> adultList = new ArrayList<>();
    filteredPersonList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(fireStationIterator -> {
            if (fireStationIterator.getBirthdate() != null && !fireStationIterator.getBirthdate().isEmpty()) {
              String birthdate = fireStationIterator.getBirthdate();
              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
              Date d;
              try {
                d = sdf.parse(birthdate);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int date = c.get(Calendar.DATE);
                LocalDate l1 = LocalDate.of(year, month, date);
                LocalDate now1 = LocalDate.now();
                Period diff1 = Period.between(l1, now1);
                System.out.println("age: " + diff1.getYears());
                personIterator.setAge(diff1.getYears());
                if (diff1.getYears() <= 18) {
                  childrenList.add(personIterator);
                } else {
                  adultList.add(personIterator);
                }
              } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
          });
    });

    // We create an object including the list of children and the list of adults
    Home home = new Home(childrenList, adultList);
    if (childrenList.isEmpty()) {
      return new ArrayList<>();
    }
    List<Home> homeList = new ArrayList<>();
    homeList.add(home);



    return homeList;
  }

  public List<PersonInfo> getPersonList(String address) {

    // we retrieve the list of persons corresponding to the address
    List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);

    filteredPersonList.forEach(personIterator -> {
      medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
          personIterator.getFirstName(), personIterator.getLastName()).forEach(medicalRecordIterator -> {
            if (medicalRecordIterator.getBirthdate() != null && !medicalRecordIterator.getBirthdate().isEmpty()) {
              String birthdate = medicalRecordIterator.getBirthdate();
              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
              Date d;
              try {
                d = sdf.parse(birthdate);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int date = c.get(Calendar.DATE);
                LocalDate l1 = LocalDate.of(year, month, date);
                LocalDate now1 = LocalDate.now();
                Period diff1 = Period.between(l1, now1);
                System.out.println("age: " + diff1.getYears());
                personIterator.setAge(diff1.getYears());
                personIterator.setMedications(medicalRecordIterator.getMedications());
                personIterator.setAllergies(medicalRecordIterator.getAllergies());
              } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
          });
    });

    List<FireStation> filteredFireStationList = firestationRepository.findDistinctByAddress(address);

    System.out.println("filteredFireStationList: " + filteredFireStationList);

    List<Integer> fireStationNumberList = getStationNumberListFromFireStationList(filteredFireStationList);

    System.out.println("fireStationNumberList: " + fireStationNumberList);

    // We create an object including the list of persons and the list of fireStation
    // number deserving the address.
    PersonInfo personInfo = new PersonInfo(filteredPersonList, fireStationNumberList);
    List<PersonInfo> personInfoList = new ArrayList<>();
    personInfoList.add(personInfo);

    return personInfoList;
  }

  private List<Integer> getStationNumberListFromFireStationList(List<FireStation> fireStationList) {
    List<Integer> fireStationNumberList = new ArrayList<>();
    if (fireStationList != null) {
      fireStationList.forEach(fireStationIterator -> {
        if (fireStationIterator.getStation() != null) {
          fireStationNumberList.add(fireStationIterator.getStation());
        }
      });
    }
    return fireStationNumberList;
  }



}
