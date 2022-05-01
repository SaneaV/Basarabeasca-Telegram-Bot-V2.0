package md.basarabeasca.bot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PhoneNumberDto {

  private Long id;
  private String phoneNumber;
  private String description;
}
