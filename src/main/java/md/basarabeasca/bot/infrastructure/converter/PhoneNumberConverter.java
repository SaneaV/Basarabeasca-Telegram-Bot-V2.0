package md.basarabeasca.bot.infrastructure.converter;

import java.util.List;
import md.basarabeasca.bot.dao.domain.PhoneNumber;

public interface PhoneNumberConverter {

  String toMessage(List<PhoneNumber> phoneNumbers);
}
