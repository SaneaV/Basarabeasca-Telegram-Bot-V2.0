package md.basarabeasca.bot.infrastructure.service;

import java.util.List;
import md.basarabeasca.bot.dao.domain.PhoneNumber;

public interface PhoneNumberService {

  List<PhoneNumber> getNextPage(Long page);

  List<PhoneNumber> getPreviousPage(Long page);

  String addNumber(String number, String description);

  String deleteNumber(String number);

  List<PhoneNumber> findByDescription(String description);

  Long getLastIdOnPage(Long page);

  Long getLastId();
}
