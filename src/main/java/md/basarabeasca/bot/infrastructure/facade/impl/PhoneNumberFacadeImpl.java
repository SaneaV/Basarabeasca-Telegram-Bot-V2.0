package md.basarabeasca.bot.infrastructure.facade.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.PhoneNumber;
import md.basarabeasca.bot.infrastructure.converter.PhoneNumberConverter;
import md.basarabeasca.bot.infrastructure.facade.PhoneNumberFacade;
import md.basarabeasca.bot.infrastructure.service.PhoneNumberService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberFacadeImpl implements PhoneNumberFacade {

  private static final String ADD_NUMBER_REGEX = "(/addNumber)\\s(0\\d{8})\\s([A-Za-z0-9-А-Яа-я,.()ĂÂÎȘȚăâîșț\\s]+)";
  private static final String INCORRECT_NUMBER = "Номер некоректен";

  private final PhoneNumberService phoneNumberService;
  private final PhoneNumberConverter phoneNumberConverter;

  @Override
  public String getNextPage(Long startId) {
    final List<PhoneNumber> phoneNumbers = getPhoneNumbersFrom(startId);
    return phoneNumberConverter.toMessage(phoneNumbers);
  }

  @Override
  public String getPreviousPage(Long stopId) {
    final List<PhoneNumber> phoneNumbers = getPhoneNumbersTo(stopId);
    return phoneNumberConverter.toMessage(phoneNumbers);
  }

  @Override
  public String addNumber(String message) {
    final Pattern pattern = Pattern.compile(ADD_NUMBER_REGEX);
    final Matcher matcher = pattern.matcher(message);

    if (matcher.find()) {
      final String number = matcher.group(2);
      final String description = matcher.group(3);
      return phoneNumberService.addNumber(number, description);
    } else {
      return INCORRECT_NUMBER;
    }
  }

  @Override
  public long getMaxIdOnPage(Long startId) {
    final long maxIdOnPage = phoneNumberService.getMaxIdOnPage(startId);
    final long lastId = phoneNumberService.getLastId();

    return lastId == maxIdOnPage ? 0L : maxIdOnPage;
  }

  @Override
  public long getMinIdOnPage(Long stopId) {
    if (stopId == 0) {
      return (long) (Math.floor(phoneNumberService.getLastId() / 10.0) * 10);
    }
    final long minIdOnPage = phoneNumberService.getMinIdOnPage(stopId);
    final long firstId = phoneNumberService.getFirstId();

    return firstId == minIdOnPage ? 10L : minIdOnPage;
  }

  private List<PhoneNumber> getPhoneNumbersFrom(Long startId) {
    final List<PhoneNumber> phoneNumbers = phoneNumberService.getNextPage(startId);

    return phoneNumbers.isEmpty() ? phoneNumberService.getNextPage(0L) : phoneNumbers;
  }

  private List<PhoneNumber> getPhoneNumbersTo(Long stopId) {
    final List<PhoneNumber> phoneNumbers = phoneNumberService.getPreviousPage(stopId);

    return phoneNumbers.isEmpty() ? phoneNumberService.getNextPage(0L) : phoneNumbers;
  }
}
