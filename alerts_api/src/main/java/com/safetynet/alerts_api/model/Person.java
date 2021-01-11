package com.safetynet.alerts_api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
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
import lombok.Data;

@Data
@Entity
@JsonFilter("dynamicFilter")
@Table(name = "persons")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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


  public void setAge_Medications_Allergies(MedicalRecord medicalRecord, LocalDate currentDate) {
    String birthdate = medicalRecord.getBirthdate();
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
      this.setMedications(medicalRecord.getMedications());
      this.setAllergies(medicalRecord.getAllergies());
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


}