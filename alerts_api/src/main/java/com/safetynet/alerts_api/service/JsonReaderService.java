package com.safetynet.alerts_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts_api.model.Person;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JsonReaderService {

  private ObjectMapper objectMapper;

  @Value("${data.jsonFilePath}")
  private String filePath;

  private static final Logger logger = LogManager.getLogger(JsonReaderService.class);

  @Autowired
  private PersonService personService;

  public void readDataFromJsonFile() {
    logger.debug("Démarrage du chargement du fichier data.json");

    try {
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(this.filePath));

      JSONParser jsonParser = new JSONParser();

      JSONObject jsonObject = (JSONObject) jsonParser.parse(inputStreamReader);

      List<Person> lstPerson = readListPersonFromJsonObject(jsonObject);
      personService.saveAllPersons(lstPerson);

      inputStreamReader.close();

    } catch (IOException | ParseException exception) {
      logger.error("Error while parsing input json file : " + exception.getMessage() + " Stack Strace : "
          + exception.getStackTrace());
    }

    logger.debug("Chargement du fichier data.json terminé");
  }

  private List<Person> readListPersonFromJsonObject(JSONObject jsonObject) {
    JSONArray personsInJson = (JSONArray) jsonObject.get("persons");

    objectMapper = new ObjectMapper();
    List<Person> personList = new ArrayList<>();

    for (Object person : personsInJson) {
      try {
        personList.add(objectMapper.readValue(person.toString(), Person.class));
      } catch (JsonProcessingException exception) {
        logger.error("Error while parsing input json file - persons : " + exception.getMessage() + " Stack Strace : "
            + exception.getStackTrace());
      }
    }
    ;

    return personList;
  }


}
