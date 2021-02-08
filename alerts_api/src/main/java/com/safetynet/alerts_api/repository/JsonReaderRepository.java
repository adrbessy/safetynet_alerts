package com.safetynet.alerts_api.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts_api.model.FireStation;
import com.safetynet.alerts_api.model.MedicalRecord;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.service.fireStation.FireStationServiceImpl;
import com.safetynet.alerts_api.service.medicalRecord.MedicalRecordServiceImpl;
import com.safetynet.alerts_api.service.person.PersonService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import constants.FilesPath;

@Repository
public class JsonReaderRepository {

  private ObjectMapper objectMapper;

  private static final Logger logger = LogManager.getLogger(JsonReaderRepository.class);

  @Autowired
  private PersonService personService;

  @Autowired
  private FireStationServiceImpl fireStationService;

  @Autowired
  private MedicalRecordServiceImpl medicalRecordService;

  public void readDataFromJsonFile() {
    logger.debug("execution start of the readDataFromJsonFile method in JsonReaderRepository");

    try {
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(FilesPath.ORIGINAL_INPUT_FILE));
      JSONParser jsonParser = new JSONParser();
      JSONObject jsonObject = (JSONObject) jsonParser.parse(inputStreamReader);

      List<Person> lstPerson = readListPersonFromJsonObject(jsonObject);
      personService.saveAllPersons(lstPerson);

      List<FireStation> lstFireStation = readListFireStationFromJsonObject(jsonObject);
      fireStationService.saveAllFireStations(lstFireStation);

      List<MedicalRecord> lstMedicalRecords = readListMedicalRecordFromJsonObject(jsonObject);
      medicalRecordService.saveAllMedicalRecords(lstMedicalRecords);

      inputStreamReader.close();
    } catch (IOException | ParseException exception) {
      logger.error("Error while parsing input json file : " + exception.getMessage());
    }

    logger.debug("execution end of the readDataFromJsonFile method in JsonReaderRepository");
  }

  private List<Person> readListPersonFromJsonObject(JSONObject jsonObject) {
    JSONArray personsInJson = (JSONArray) jsonObject.get("persons");

    objectMapper = new ObjectMapper();
    List<Person> personList = new ArrayList<>();

    for (Object person : personsInJson) {
      try {
        personList.add(objectMapper.readValue(person.toString(), Person.class));
      } catch (JsonProcessingException exception) {
        logger.error("Error while parsing input json file - persons : " + exception.getMessage());
      }
    }
    LinkedHashSet<Person> hashSet = new LinkedHashSet<>(personList);
    List<Person> listWithoutDuplicates = new ArrayList<>(hashSet);

    return listWithoutDuplicates;
  }

  private List<FireStation> readListFireStationFromJsonObject(JSONObject jsonObject) {
    JSONArray fireStationsArrayInJson = (JSONArray) jsonObject.get("firestations");

    objectMapper = new ObjectMapper();
    List<FireStation> fireStationList = new ArrayList<>();

    for (Object fireStation : fireStationsArrayInJson) {
      try {
        fireStationList.add(objectMapper.readValue(fireStation.toString(), FireStation.class));

      } catch (JsonProcessingException exception) {
        logger.error("Error while parsing input json file - firestations : " + exception.getMessage());
      }
    }
    LinkedHashSet<FireStation> hashSet = new LinkedHashSet<>(fireStationList);
    List<FireStation> listWithoutDuplicates = new ArrayList<>(hashSet);

    return listWithoutDuplicates;
  }

  private List<MedicalRecord> readListMedicalRecordFromJsonObject(JSONObject jsonObject) {
    JSONArray medicalRecordsArrayInJson = (JSONArray) jsonObject.get("medicalrecords");

    objectMapper = new ObjectMapper();
    List<MedicalRecord> medicalRecordList = new ArrayList<>();

    for (Object medicalRecord : medicalRecordsArrayInJson) {
      try {
        medicalRecordList.add(objectMapper.readValue(medicalRecord.toString(), MedicalRecord.class));
      } catch (JsonProcessingException exception) {
        logger.error("Error while parsing input json file - medicalRecords : " + exception.getMessage());
      }
    }
    LinkedHashSet<MedicalRecord> hashSet = new LinkedHashSet<>(medicalRecordList);
    List<MedicalRecord> listWithoutDuplicates = new ArrayList<>(hashSet);

    return listWithoutDuplicates;
  }

}
