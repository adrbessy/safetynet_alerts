package com.safetynet.alerts_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.home.HomeService;
import com.safetynet.alerts_api.service.personInfo.PersonInfoService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class HomeServiceTest {

  @Autowired
  private HomeService homeService;

  @MockBean
  private PersonRepository personRepositoryMock;

  @MockBean
  private PersonInfoService personInfoServiceMock;

  /**
   * test to get a person list by address.
   * 
   */
  @Test
  public void testGetPersonListByAddress() {
    Person person = new Person();
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    String address = "1 rue antonio vivaldi";

    when(personRepositoryMock.findDistinctByAddress(address)).thenReturn(personList);
    doNothing().when(personInfoServiceMock).setAgeAndMedicationsAndAllergiesFromPersonList(personList,
        LocalDate.now());

    List<Person> result = homeService.getPersonListByAddress(address);
    verify(personRepositoryMock,
        Mockito.times(1)).findDistinctByAddress(address);
    verify(personInfoServiceMock,
        Mockito.times(1)).setAgeAndMedicationsAndAllergiesFromPersonList(personList, LocalDate.now());
    assertThat(result).isEqualTo(personList);
  }

}
