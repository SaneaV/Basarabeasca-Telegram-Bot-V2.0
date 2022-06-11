package md.basarabeasca.bot.web.converter;

import java.util.List;
import md.basarabeasca.bot.dao.domain.ExchangeRate;

public interface ExchangeRateConverter {

  String toMessage(List<ExchangeRate> exchangeRate);
}