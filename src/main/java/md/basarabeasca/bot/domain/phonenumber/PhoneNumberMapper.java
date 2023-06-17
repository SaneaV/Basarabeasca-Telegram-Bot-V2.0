package md.basarabeasca.bot.domain.phonenumber;

import md.basarabeasca.bot.infrastructure.jpa.PhoneNumberJpa;

public interface PhoneNumberMapper {

  PhoneNumber toEntity(PhoneNumberJpa phoneNumberJpa);
}
