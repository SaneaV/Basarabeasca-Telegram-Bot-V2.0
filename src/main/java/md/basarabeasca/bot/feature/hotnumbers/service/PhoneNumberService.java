package md.basarabeasca.bot.feature.hotnumbers.service;

import md.basarabeasca.bot.feature.hotnumbers.dao.model.PhoneNumber;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;

import java.util.List;

public interface PhoneNumberService {

    List<PhoneNumberDto> getAllNumbers();

    List<PhoneNumber> getNextPage(Long page);

    List<PhoneNumber> getPreviousPage(Long page);

    String addNumber(String number, String description);

    String deleteNumber(String number);

    List<PhoneNumberDto> findByDescription(String description);
}
