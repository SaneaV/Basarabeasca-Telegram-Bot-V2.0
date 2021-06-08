package md.basarabeasca.bot.feature.hotnumbers.service;

import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;

import java.util.List;

public interface PhoneNumberService {

    List<PhoneNumberDto> getAllNumbers();

    String addNumber(String number, String description);

    String deleteNumber(String number);

    List<PhoneNumberDto> findByDescription(String description);
}
