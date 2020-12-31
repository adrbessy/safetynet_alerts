package com.safetynet.alerts_api.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JsonReaderService {

  @Value("${data.jsonFilePath}")
  private String filePath;

  private static final Logger logger = LogManager.getLogger(JsonReaderService.class);

  public void readDataFromJsonFile() {
    logger.debug("Démarrage du chargement du fichier data.json");

    try {
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(this.filePath));

      JSONParser jsonParser = new JSONParser();

      JSONObject jsonObject = (JSONObject) jsonParser.parse(inputStreamReader);

      inputStreamReader.close();

    } catch (IOException | ParseException exception) {
      logger.error("Error while parsing input json file : " + exception.getMessage() + " Stack Strace : "
          + exception.getStackTrace());
    }

    logger.debug("Chargement du fichier data.json terminé");
  }


}
