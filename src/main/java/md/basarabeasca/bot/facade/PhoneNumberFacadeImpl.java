package md.basarabeasca.bot.facade;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.phonenumber.PhoneNumber;
import md.basarabeasca.bot.facade.api.PhoneNumberFacade;
import md.basarabeasca.bot.infrastructure.converter.api.PhoneNumberConverter;
import md.basarabeasca.bot.infrastructure.service.api.PhoneNumberService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberFacadeImpl implements PhoneNumberFacade {

  public final static String PHONE_NUMBER_LIST_IS_EMPTY_OR_NUMBER_WAS_NOT_FOUND = "Список номеров пуст или запрашиваемый вами номер не был найден";
  private static final String ADD_NUMBER_REGEX = "(/addNumber)\\s(0\\d{8})\\s([A-Za-z0-9-А-Яа-я,.()ĂÂÎȘȚăâîșț\\s]+)";
  private static final String DELETE_NUMBER_REGEX = "(/deleteNumber)\\s(0\\d{8})";
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
    final Matcher matcher = getMatcher(ADD_NUMBER_REGEX, message);

    if (matcher.find()) {
      final String number = matcher.group(2);
      final String description = matcher.group(3);
      return phoneNumberService.addNumber(number, description);
    } else {
      return INCORRECT_NUMBER;
    }
  }

  @Override
  public String deleteNumber(String message) {
    final Matcher matcher = getMatcher(DELETE_NUMBER_REGEX, message);

    if (matcher.find()) {
      final String number = matcher.group(2);
      return phoneNumberService.deleteNumber(number);
    } else {
      return INCORRECT_NUMBER;
    }
  }

  @Override
  public String findByDescription(String description) {
    final List<PhoneNumber> numbers = phoneNumberService.findByDescription(description);
    if (numbers.isEmpty()) {
      return PHONE_NUMBER_LIST_IS_EMPTY_OR_NUMBER_WAS_NOT_FOUND;
    }
    return phoneNumberConverter.toMessage(numbers);
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

  private Matcher getMatcher(String stringPattern, String message) {
    final Pattern pattern = Pattern.compile(stringPattern);
    return pattern.matcher(message);
  }
}
