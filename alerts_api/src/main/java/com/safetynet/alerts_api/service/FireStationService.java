package com.safetynet.alerts_api.service;

import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.FireStationInfo;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.FireStationRepository;
import com.safetynet.alerts_api.repository.MedicalRecordRepository;
import com.safetynet.alerts_api.repository.PersonRepository;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationService {

  private static final Logger logger = LogManager.getLogger(FireStationService.class);

  @Autowired
  private FireStationRepository fireStationRepository;

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  /**
   * Sauvegarder la liste des stations de feu
   *
   * @param fireStationsList Liste à sauvegarder
   * @return true si la sauvegarde s'est bien passée, false en cas d'erreur
   */
  public boolean saveAllFireStations(List<FireStation> fireStationsList) {
    if (fireStationsList != null && !fireStationsList.isEmpty()) {
      try {
        fireStationRepository.saveAll(fireStationsList);
        return true;
      } catch (Exception exception) {
        logger.error("Erreur lors de l'enregistrement de la liste des personnes " + exception.getMessage()
            + " , Stack Trace : " + exception.getStackTrace());
      }
    }
    return false;
  }

  public List<FireStationInfo> getFireStationPersonList(Integer stationNumber) {
    if (stationNumber != null) {
      try {
        // we retrieve the list of stations corresponding to the stationNumber
        List<FireStation> fireStationList = fireStationRepository.findDistinctByStation(stationNumber);

        // we retrieve the address list corresponding to the fireStation list
        List<String> addressList = getAddressListFromFireStationList(fireStationList);

        // we retrieve the person list corresponding to the address list
        List<Person> filteredPersonList = personRepository.findAllByAddressInOrderByAddress(addressList);

        // we retrieve the birthDateList corresponding to the filteredPersonList
        List<String> birthDateList = new ArrayList<>();
        filteredPersonList.forEach(personIterator -> {
          medicalRecordRepository.findByFirstNameAndLastNameAllIgnoreCase(
              personIterator.getFirstName(), personIterator.getLastName()).forEach(fireStationIterator -> {
                if (fireStationIterator.getBirthdate() != null && !fireStationIterator.getBirthdate().isEmpty()) {
                  birthDateList.add(fireStationIterator.getBirthdate());
                }
              });
        });
        System.out.println(birthDateList);

        // we retrieve the age, then the number of children and adults
        int child = 0;
        int adult = 0;
        for (String temp : birthDateList) {
          SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
          Date d = sdf.parse(temp);
          Calendar c = Calendar.getInstance();
          c.setTime(d);
          int year = c.get(Calendar.YEAR);
          int month = c.get(Calendar.MONTH) + 1;
          int date = c.get(Calendar.DATE);
          LocalDate l1 = LocalDate.of(year, month, date);
          LocalDate now1 = LocalDate.now();
          Period diff1 = Period.between(l1, now1);
          System.out.println("age:" + diff1.getYears());
          if (diff1.getYears() <= 18)
            child++;
          else
            adult++;
        }

        // We create an object including the list of persons and the number of adults
        // and children
        FireStationInfo fireStationInfo = new FireStationInfo(filteredPersonList,
            child, adult);
        List<FireStationInfo> fireStationInfoList = new ArrayList<>();
        fireStationInfoList.add(fireStationInfo);

        return fireStationInfoList;

      } catch (Exception exception) {
        logger.error("Erreur lors de la récupération des personnes liées à une station de feu : "
            + exception.getMessage() + " Stack Trace : " + exception.getStackTrace());
        return null;
      }
    } else {
      return null;
    }
  }


  private List<String> getAddressListFromFireStationList(List<FireStation> fireStationList) {
    List<String> addressList = new ArrayList<>();
    if (fireStationList != null) {
      fireStationList.forEach(fireStationIterator -> {
        if (fireStationIterator.getAddress() != null && !fireStationIterator.getAddress().isEmpty()) {
          addressList.add(fireStationIterator.getAddress());
        }
      });
    }
    return addressList;
  }

  private List<String> getPhoneListFromPersonList(List<Person> personList) {
    List<String> phoneList = new ArrayList<>();
    if (personList != null) {
      personList.forEach(personIterator -> {
        if (personIterator.getPhone() != null && !personIterator.getPhone().isEmpty()) {
          phoneList.add(personIterator.getPhone());
        }
      });
    }
    return phoneList;
  }

  public List<String> getPhoneNumberList(Integer firestation) {
    // we retrieve the list of stations corresponding to the stationNumber
    List<FireStation> fireStationList = fireStationRepository.findDistinctByStation(firestation);

    // we retrieve the address list corresponding to the fireStation list
    List<String> addressList = getAddressListFromFireStationList(fireStationList);

    // we retrieve the person list corresponding to the address list
    List<Person> filteredPersonList = personRepository.findAllByAddressInOrderByAddress(addressList);

    // we retrieve the address list corresponding to the filteredPerson list
    List<String> phoneList = getPhoneListFromPersonList(filteredPersonList);

    List<String> phoneListNoDuplicates = phoneList.stream().distinct().collect(Collectors.toList());

    System.out.println("phoneListNoDuplicates:" + phoneListNoDuplicates);

    return phoneListNoDuplicates;
  }



}

