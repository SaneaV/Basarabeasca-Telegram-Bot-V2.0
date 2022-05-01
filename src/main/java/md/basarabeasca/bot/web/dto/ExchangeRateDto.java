package md.basarabeasca.bot.web.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExchangeRateDto {

  @JsonInclude(NON_EMPTY)
  private String bankName;
  private String currency;
  private String purchase;
  @JsonInclude(NON_EMPTY)
  private String sale;
}
