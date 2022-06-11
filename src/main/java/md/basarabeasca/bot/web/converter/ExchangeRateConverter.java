package md.basarabeasca.bot.web.converter;

import java.util.List;
import md.basarabeasca.bot.domain.ExchangeRate;

public interface ExchangeRateConverter {

  String toMessage(List<ExchangeRate> exchangeRate);
}