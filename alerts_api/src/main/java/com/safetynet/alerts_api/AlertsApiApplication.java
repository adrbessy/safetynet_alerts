package com.safetynet.alerts_api;

import com.safetynet.alerts_api.repository.JsonReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AlertsApiApplication implements CommandLineRunner {

  @Autowired
  private JsonReaderRepository jsonReaderRepository;

  public static void main(String[] args) {
    SpringApplication.run(AlertsApiApplication.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {
    jsonReaderRepository.readDataFromJsonFile();
  }
}
