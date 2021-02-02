package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.email.EmailService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class EmailServiceTest {

  @Autowired
  private EmailService emailService;

  @MockBean
  private PersonRepository personRepositoryMock;

  private Person person;
  private List<Person> personList;
  List<String> emailList;

  @BeforeEach
  private void setUp() {
    person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    personList = new ArrayList<>();
    personList.add(person);
    emailList = new ArrayList<>();
    emailList.add("ballapolorra-7977@yopmail.com");
  }

  /**
   * test to get a list of email from a list of persons.
   * 
   */
  @Test
  public void testGetEmailListFromPersonList() {
    List<String> result = emailService.getEmailListFromPersonList(personList);
    assertThat(result).isEqualTo(emailList);
  }


  /**
   * test to get a list of email of all persons living in a given city.
   * 
   */
  @Test
  public void testGetPersonEmailFromCity() {
    when(personRepositoryMock.findDistinctByCityAllIgnoreCase("Paris")).thenReturn(personList);

    List<String> result = emailService.getPersonEmailFromCity("Paris");
    assertThat(result).isEqualTo(emailList);
  }

}
