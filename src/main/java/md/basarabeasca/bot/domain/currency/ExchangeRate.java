package md.basarabeasca.bot.domain.currency;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ExchangeRate implements Serializable {

  private final String bankName;
  private final String currency;
  private final String purchase;
  private final String sale;
}
