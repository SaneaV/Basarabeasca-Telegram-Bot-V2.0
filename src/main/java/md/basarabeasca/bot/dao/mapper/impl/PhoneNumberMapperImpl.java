package md.basarabeasca.bot.dao.mapper.impl;

import md.basarabeasca.bot.dao.domain.PhoneNumber;
import md.basarabeasca.bot.dao.repository.model.PhoneNumberJpa;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberMapperImpl implements PhoneNumberMapper {

  @Override
  public PhoneNumber toEntity(PhoneNumberJpa phoneNumberJpa) {
    return PhoneNumber.builder()
        .id(phoneNumberJpa.getId())
        .phoneNumber(phoneNumberJpa.getPhoneNumber())
        .description(phoneNumberJpa.getDescription())
        .build();
  }
}
