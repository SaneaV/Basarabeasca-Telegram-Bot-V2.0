package md.basarabeasca.bot.infrastructure.facade.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.PhoneNumber;
import md.basarabeasca.bot.infrastructure.converter.PhoneNumberConverter;
import md.basarabeasca.bot.infrastructure.facade.PhoneNumberFacade;
import md.basarabeasca.bot.infrastructure.service.PhoneNumberService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberFacadeImpl implements PhoneNumberFacade {

  private final PhoneNumberService phoneNumberService;
  private final PhoneNumberConverter phoneNumberConverter;

  @Override
  public String getNextPage(Long startId) {
    final List<PhoneNumber> phoneNumbers = getPhoneNumbersFrom(startId);
    return phoneNumberConverter.toMessage(phoneNumbers);
  }

  @Override
  public long getLastId(Long startId) {
    final long lastIdOnPage = phoneNumberService.getLastIdOnPage(startId);
    final long lastId = phoneNumberService.getLastId();

    return lastId == lastIdOnPage ? 0L : lastIdOnPage;
  }

  private List<PhoneNumber> getPhoneNumbersFrom(Long startId) {
    final List<PhoneNumber> phoneNumbers = phoneNumberService.getNextPage(startId);

    return phoneNumbers.isEmpty() ? phoneNumberService.getNextPage(0L) : phoneNumbers;
  }
}
