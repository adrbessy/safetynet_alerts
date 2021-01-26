package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.person.PersonService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PersonServiceTest {

  @Autowired
  private PersonService personService;

  @MockBean
  private PersonRepository personRepositoryMock;


  /**
   * test to get a person.
   * 
   */
  @Test
  public void testGetPerson() {
    long id = 54;
    Person person = new Person();
    person.setId(id);
    Optional<Person> optionalPerson = Optional.of(person);
    when(personRepositoryMock.findById(id)).thenReturn(optionalPerson);
    Person person2 = personService.getPerson(id);
    assertThat(person2).isEqualTo(person);
  }


  /**
   * test to save a person.
   * 
   */
  @Test
  public void testSavePerson() {
    Person person = new Person();
    when(personRepositoryMock.save(person)).thenReturn(person);
    Person person2 = personService.savePerson(person);
    assertThat(person2).isEqualTo(person);
  }


  /**
   * test to delete a person.
   * 
   */
  @Test
  public void testDeletePerson() {
    doNothing().when(personRepositoryMock).deletePersonByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy");
    personService.deletePerson("Adrien", "Bessy");
    verify(personRepositoryMock,
        Mockito.times(1)).deletePersonByFirstNameAndLastNameAllIgnoreCase("Adrien", "Bessy");
  }

}