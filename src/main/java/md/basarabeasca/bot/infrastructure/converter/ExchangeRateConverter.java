package md.basarabeasca.bot.infrastructure.converter;

import java.util.List;
import java.util.Map;
import md.basarabeasca.bot.dao.domain.ExchangeRate;

public interface ExchangeRateConverter {

  String toMessage(List<ExchangeRate> exchangeRate);

  List<String> toMessage(Map<String, List<ExchangeRate>> exchangeRates);

  String toMessage(ExchangeRate exchangeRate, String action, String currency);
}