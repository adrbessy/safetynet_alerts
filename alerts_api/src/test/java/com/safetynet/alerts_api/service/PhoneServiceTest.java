package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.service.phone.PhoneServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhoneServiceTest {

  private PhoneServiceImpl phoneService;

  @BeforeEach
  private void setUp() {
    phoneService = new PhoneServiceImpl();
  }

  /**
   * test to get a list of phone number from a list of person.
   * 
   */
  @Test
  public void testGetPhoneListFromPersonList() {
    Person person = new Person();
    person.setEmail("ballapolorra-7977@yopmail.com");
    person.setPhone("355-96-61");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    Person person2 = new Person();
    person2.setEmail("unoddicab-5625@yopmail.com");
    person2.setPhone("653-47-37");
    personList.add(person2);
    Person person3 = new Person();
    person3.setEmail("unoddicab-5625@yopmail.com");
    person3.setPhone("896-86-29");
    personList.add(person3);
    List<String> phoneList = new ArrayList<>();
    phoneList.add("355-96-61");
    phoneList.add("653-47-37");
    phoneList.add("896-86-29");
    List<String> result = phoneService.getPhoneListFromPersonList(personList);
    assertThat(result).isEqualTo(phoneList);
  }

}
