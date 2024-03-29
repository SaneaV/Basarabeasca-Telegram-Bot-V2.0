package md.basarabeasca.bot.domain.phonenumber;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class PhoneNumber {

  private final Long id;
  private final String phoneNumber;
  private final String description;
}
