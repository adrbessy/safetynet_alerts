package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.service.email.EmailServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailServiceTest {

  private EmailServiceImpl emailService;

  @BeforeEach
  private void setUp() {
    emailService = new EmailServiceImpl();
  }

  /**
   * test to get a list of email from a list of persons.
   * 
   */
  @Test
  public void testGetEmailListFromPersonList() {
    Person person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    Person person2 = new Person();
    person2.setEmail("unoddicab-5625@yopmail.com");
    personList.add(person2);
    Person person3 = new Person();
    // to check if the method deletes duplicates:
    person3.setEmail("unoddicab-5625@yopmail.com");
    personList.add(person3);
    List<String> emailList = new ArrayList<>();
    emailList.add("ballapolorra-7977@yopmail.com");
    emailList.add("unoddicab-5625@yopmail.com");
    List<String> result = emailService.getEmailListFromPersonList(personList);
    assertThat(result).isEqualTo(emailList);
  }


  /**
   * test to get a list of email of all persons living in a given city.
   * 
   */
  @Test
  public void testGetPersonEmailFromCity() {
    /*
     * Person person = new Person(); person.setId((long) 56);
     * person.setFirstName("Adrien"); person.setLastName("Bessy");
     * person.setAddress("82 Alexander Road"); person.setCity("New York");
     * person.setEmail("ballapolorra-7977@yopmail.com"); List<Person> personList =
     * new ArrayList<>(); personList.add(person); Person person2 = new Person();
     * person2.setId((long) 97); person2.setFirstName("Christian");
     * person2.setLastName("Legal"); person2.setAddress("12 rue des ecoles");
     * person2.setCity("Paris"); person2.setEmail("unoddicab-5625@yopmail.com");
     * personList.add(person2); Person person3 = new Person(); person3.setId((long)
     * 97); person3.setFirstName("Christian"); person3.setLastName("Legal");
     * person3.setAddress("12 rue des ecoles"); person3.setCity("Paris");
     * person3.setEmail("unoddicab-5625@yopmail.com"); personList.add(person3);
     * List<String> emailList = new ArrayList<>();
     * emailList.add("ballapolorra-7977@yopmail.com");
     * emailList.add("unoddicab-5625@yopmail.com"); List<String> result =
     * emailService.getPersonEmailFromCity("Paris");
     * assertThat(result).isEqualTo(emailList);
     */
  }

}
