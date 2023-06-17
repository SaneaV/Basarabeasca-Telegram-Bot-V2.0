package md.basarabeasca.bot.infrastructure.converter.api;

import java.util.List;
import md.basarabeasca.bot.domain.phonenumber.PhoneNumber;

public interface PhoneNumberConverter {

  String toMessage(List<PhoneNumber> phoneNumbers);
}
