package md.basarabeasca.bot.web.converter.impl;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import md.basarabeasca.bot.dao.domain.PhoneNumber;
import md.basarabeasca.bot.web.converter.PhoneNumberConverter;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberConverterImpl implements PhoneNumberConverter {

  private final static String PHONE_NUMBER_TEMPLATE = "%s. %s - %s\n";

  @Override
  public String toMessage(List<PhoneNumber> phoneNumbers) {
    final AtomicInteger id = new AtomicInteger(0);
    return phoneNumbers.stream()
        .map(phoneNumber -> String.format(PHONE_NUMBER_TEMPLATE, id.incrementAndGet(),
            phoneNumber.getPhoneNumber(), phoneNumber.getDescription()))
        .collect(joining(EMPTY));
  }
}
