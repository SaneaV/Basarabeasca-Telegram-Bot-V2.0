package md.basarabeasca.bot.infrastructure.service.api;

import java.util.List;
import md.basarabeasca.bot.domain.phonenumber.PhoneNumber;

public interface PhoneNumberService {

  List<PhoneNumber> getNextPage(Long page);

  List<PhoneNumber> getPreviousPage(Long page);

  String addNumber(String number, String description);

  String deleteNumber(String number);

  List<PhoneNumber> findByDescription(String description);

  Long getMaxIdOnPage(Long page);

  Long getMinIdOnPage(Long page);

  Long getLastId();

  Long getFirstId();
}
