package md.basarabeasca.bot.infrastructure.converter;

import java.util.List;
import md.basarabeasca.bot.dao.domain.ExchangeRate;

public interface ExchangeRateConverter {

  String toMessage(List<ExchangeRate> exchangeRate);

  String toMessage(ExchangeRate exchangeRate, String action, String currency);
}