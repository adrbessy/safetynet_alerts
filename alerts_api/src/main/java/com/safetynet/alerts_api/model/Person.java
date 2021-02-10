package com.safetynet.alerts_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Data;

@Data
@Entity
@Table(name = "persons")
public class Person {

  private static final Logger logger = LogManager.getLogger(Person.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  private String firstName;

  private String lastName;

  private String address;

  private String city;

  private String zip;

  private String phone;

  private String email;

  private int age;

  @ElementCollection
  private List<String> medications;

  @ElementCollection
  private List<String> allergies;


  public void setAge(String birthdate, LocalDate currentDate) {
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
      Period diff1 = Period.between(l1, currentDate);
      this.setAge(diff1.getYears());
    } catch (ParseException e) {
      logger.error("setAge error : " + e);
    }
  }

  public void setMedicationsAndAllergies(MedicalRecord medicalRecord) {
    try {
      this.setMedications(medicalRecord.getMedications());
      this.setAllergies(medicalRecord.getAllergies());
    } catch (Exception e) {
      logger.error("setMedications error : " + e);
    }
  }


}