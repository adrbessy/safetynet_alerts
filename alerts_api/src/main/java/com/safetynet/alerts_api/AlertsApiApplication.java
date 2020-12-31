package com.safetynet.alerts_api;

import com.safetynet.alerts_api.service.JsonReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlertsApiApplication implements CommandLineRunner {

  @Autowired
  private JsonReaderService jsonReaderService;

  public static void main(String[] args) {
    SpringApplication.run(AlertsApiApplication.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {
    jsonReaderService.readDataFromJsonFile();
  }
}
