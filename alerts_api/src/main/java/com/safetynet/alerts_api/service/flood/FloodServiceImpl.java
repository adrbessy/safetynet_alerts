package com.safetynet.alerts_api.service.flood;

import com.safetynet.alerts_api.model.FireDTO;
import com.safetynet.alerts_api.model.FireDTOByAddress;
import com.safetynet.alerts_api.model.Person;
import com.safetynet.alerts_api.service.address.AddressServiceImpl;
import com.safetynet.alerts_api.service.home.HomeServiceImpl;
import com.safetynet.alerts_api.service.map.MapService;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloodServiceImpl implements FloodService {

  private static final Logger logger = LogManager.getLogger(FloodServiceImpl.class);

  @Autowired
  private AddressServiceImpl addressService;

  @Autowired
  private HomeServiceImpl homeService;

  @Autowired
  private MapService mapService;

  @Override
  public List<FireDTOByAddress> getPersonInfoByAddressList(List<Integer> stationNumberList) {
    logger.debug("in the method getPersonInfoByAddressList in the class FloodServiceImpl");
    // We create an object including the list of persons and the list of fireStation
    // number deserving the address.
    List<FireDTOByAddress> personInfoByAddressList = new ArrayList<>();
    try {
      List<String> addressList = addressService.getAddressListFromStationNumberList(stationNumberList);

      if (addressList != null) {
        addressList.forEach(addressIterator -> {
          List<Person> personList = homeService.getPersonListByAddress(addressIterator);
          List<FireDTO> fireDTOList = mapService.convertToFireDTOList(personList);

          FireDTOByAddress personInfoByAddress = new FireDTOByAddress(addressIterator,
              fireDTOList);
          personInfoByAddressList.add(personInfoByAddress);
        });
      }
    } catch (Exception exception) {
      logger.error("Error when we try to get PersonInfoByAddressList :" + exception.getMessage());
    }
    return personInfoByAddressList;
  }

}
