package com.safetynet.alerts_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.JsonReaderRepository;
import com.safetynet.alerts_api.service.community.CommunityService;
import com.safetynet.alerts_api.service.email.EmailService;
import com.safetynet.alerts_api.service.person.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PersonInfoController.class)
class PersonInfoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  @MockBean
  private CommunityService communityService;

  @MockBean
  private EmailService emailService;

  @MockBean
  private JsonReaderRepository jsonReaderRepository;

  private Person person;


  @Test
  public void testDeletePerson() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/person?firstName=Jacob&lastName=Boyd"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetEmailListFromCity() throws Exception {
    mockMvc.perform(get("/communityEmail?city=culver"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetPersonInfoFromFirstNameAndLastName() throws Exception {
    mockMvc.perform(get("/personInfo?firstName=Jacob&lastName=Boyd"))
        .andExpect(status().isOk());
  }

  @Test
  public void testCreatePerson() throws Exception {
    person = new Person();
    // person.setId((long) 2);
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setAddress("82 Alexander Road");
    person.setCity("New York");
    person.setZip("1648462");
    person.setPhone("04815644");
    person.setEmail("uttoxuballo-8128@yopmail.com");

    when(personService.savePerson(person)).thenReturn(person);
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/person")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(person));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }


  @Test
  public void testUpdatePerson() throws Exception {
    person = new Person();
    person.setFirstName("Adrien");
    person.setLastName("Bessy");
    person.setAddress("82 Alexander Road");
    person.setCity("New York");
    person.setZip("1648462");
    person.setPhone("04815644");
    person.setEmail("uttoxuballo-8128@yopmail.com");

    long id = 2;
    when(personService.getPerson(id)).thenReturn(person);
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/person/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(person));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());

  }

}
