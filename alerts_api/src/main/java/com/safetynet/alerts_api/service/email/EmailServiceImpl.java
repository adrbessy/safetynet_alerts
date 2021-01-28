package com.safetynet.alerts_api.service.email;

import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public List<String> getEmailListFromPersonList(List<Person> personList) {
    List<String> emailList = new ArrayList<>();
    if (personList != null) {
      personList.forEach(personIterator -> {
        if (personIterator.getEmail() != null && !personIterator.getEmail().isEmpty()) {
          emailList.add(personIterator.getEmail());
        }
      });
    }

    List<String> emailListNoDuplicates = emailList.stream().distinct().collect(Collectors.toList());
    return emailListNoDuplicates;
  }


  @Override
  public List<String> getPersonEmailFromCity(String city) {
    List<Person> filteredPersonList = personRepository.findDistinctByCityAllIgnoreCase(city);
    List<String> emailListNoDuplicates = getEmailListFromPersonList(filteredPersonList);
    return emailListNoDuplicates;
  }

}
