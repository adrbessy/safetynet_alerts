package com.safetynet.alerts_api.service.childAlert;

import com.safetynet.alerts_api.model.ChildAlertDTO;
import com.safetynet.alerts_api.model.Home;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.repository.PersonRepository;
import com.safetynet.alerts_api.service.childrenAdults.ChildrenAdultsServiceImpl;
import com.safetynet.alerts_api.service.map.MapService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildAlertServiceImpl implements ChildAlertService {

  private static final Logger logger = LogManager.getLogger(ChildAlertServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private MapService mapService;

  @Autowired
  private ChildrenAdultsServiceImpl childrenAdultsService;

  /**
   * Get the List of children and adults living at a given address.
   * 
   * @param address An address
   * @return A Home object including a list of children and a list of adults
   */
  @Override
  public Home getChildrenListAndAdultListFromAddress(String address) {
    logger.debug("in the method getChildrenListAndAdultListFromAddress in the class ChildAlertServiceImpl");
    Home home = null;
    try {
      // we retrieve the list of persons corresponding to the address
      List<Person> filteredPersonList = personRepository.findDistinctByAddress(address);

      // we retrieve the list of children from the list of persons
      List<Person> childrenList = new ArrayList<>();
      List<Person> adultList = new ArrayList<>();
      Map<String, List<Person>> map = childrenAdultsService
          .fullChildrenListAndAdultListFromPersonList(filteredPersonList, childrenList, adultList);

      List<ChildAlertDTO> childrenDTOList = mapService.convertToChildAlertDTOList(map.get("childrenList"));

      List<ChildAlertDTO> adultDTOList = mapService.convertToChildAlertDTOList(map.get("adultList"));

      // We create an object including the list of children and the list of adults
      home = new Home(childrenDTOList, adultDTOList);
      if (childrenList.isEmpty()) {
        return new Home(new ArrayList<>(), new ArrayList<>());
      }
    } catch (Exception exception) {
      logger.error("Error when we try to get ChildrenList And AdultList From an address :" + exception.getMessage());
    }
    return home;
  }

}
