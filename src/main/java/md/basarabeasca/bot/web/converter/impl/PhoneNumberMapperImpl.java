package md.basarabeasca.bot.web.converter.impl;

import md.basarabeasca.bot.repository.model.PhoneNumberJpa;
import md.basarabeasca.bot.web.dto.PhoneNumberDto;
import md.basarabeasca.bot.web.converter.PhoneNumberMapper;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberMapperImpl implements PhoneNumberMapper {

  @Override
  public PhoneNumberDto toDto(PhoneNumberJpa phoneNumberJpa) {
    return PhoneNumberDto.builder()
        .id(phoneNumberJpa.getId())
        .phoneNumber(phoneNumberJpa.getPhoneNumber())
        .description(phoneNumberJpa.getDescription())
        .build();
  }
}
