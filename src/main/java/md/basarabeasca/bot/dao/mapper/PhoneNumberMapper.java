package md.basarabeasca.bot.dao.mapper;

import md.basarabeasca.bot.dao.repository.model.PhoneNumberJpa;
import md.basarabeasca.bot.dao.domain.PhoneNumber;

public interface PhoneNumberMapper {

  PhoneNumber toEntity(PhoneNumberJpa phoneNumberJpa);
}
