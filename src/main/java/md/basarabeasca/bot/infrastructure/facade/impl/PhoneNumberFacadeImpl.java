package md.basarabeasca.bot.infrastructure.facade.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.PhoneNumber;
import md.basarabeasca.bot.infrastructure.service.PhoneNumberService;
import md.basarabeasca.bot.infrastructure.converter.PhoneNumberConverter;
import md.basarabeasca.bot.infrastructure.facade.PhoneNumberFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberFacadeImpl implements PhoneNumberFacade {

  private final PhoneNumberService phoneNumberService;
  private final PhoneNumberConverter phoneNumberConverter;

  @Override
  public String getNextPage(Long startId) {
    final List<PhoneNumber> phoneNumbers = phoneNumberService.getNextPage(startId);
    return phoneNumberConverter.toMessage(phoneNumbers);
  }

  @Override
  public long getLastId(Long startId) {
    final List<PhoneNumber> phoneNumbers = phoneNumberService.getNextPage(startId);
    return phoneNumbers.get(phoneNumbers.size() - 1).getId() + 1;
  }
}
