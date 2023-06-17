package md.basarabeasca.bot.infrastructure.mapper;

import md.basarabeasca.bot.domain.phonenumber.PhoneNumber;
import md.basarabeasca.bot.domain.phonenumber.PhoneNumberMapper;
import md.basarabeasca.bot.infrastructure.jpa.PhoneNumberJpa;
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
