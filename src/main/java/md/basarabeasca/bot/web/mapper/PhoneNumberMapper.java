package md.basarabeasca.bot.web.mapper;

import md.basarabeasca.bot.repository.model.PhoneNumberJpa;
import md.basarabeasca.bot.web.dto.PhoneNumberDto;

public interface PhoneNumberMapper {

  PhoneNumberDto toDto(PhoneNumberJpa phoneNumberJpa);
}
