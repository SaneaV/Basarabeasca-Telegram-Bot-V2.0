package md.basarabeasca.bot.service;

import java.util.List;
import md.basarabeasca.bot.web.dto.PhoneNumberDto;

public interface PhoneNumberService {

  List<PhoneNumberDto> getNextPage(Long page);

  List<PhoneNumberDto> getPreviousPage(Long page);

  String addNumber(String number, String description);

  String deleteNumber(String number);

  List<PhoneNumberDto> findByDescription(String description);
}
