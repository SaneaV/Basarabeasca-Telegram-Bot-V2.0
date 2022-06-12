package md.basarabeasca.bot.dao.mapper;

import md.basarabeasca.bot.dao.domain.PhoneNumber;
import md.basarabeasca.bot.dao.repository.model.PhoneNumberJpa;

public interface PhoneNumberMapper {

  PhoneNumber toEntity(PhoneNumberJpa phoneNumberJpa);
}
