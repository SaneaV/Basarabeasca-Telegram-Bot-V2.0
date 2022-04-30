package md.basarabeasca.bot.service;

import md.basarabeasca.bot.web.dto.PhoneNumberDto;

import java.util.List;

public interface PhoneNumberService {

    List<PhoneNumberDto> getNextPage(Long page);

    List<PhoneNumberDto> getPreviousPage(Long page);

    String addNumber(String number, String description);

    String deleteNumber(String number);

    List<PhoneNumberDto> findByDescription(String description);
}
