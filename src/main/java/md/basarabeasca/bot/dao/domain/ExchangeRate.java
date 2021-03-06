package md.basarabeasca.bot.dao.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ExchangeRate {

  private final String bankName;
  private final String currency;
  private final String purchase;
  private final String sale;
}
