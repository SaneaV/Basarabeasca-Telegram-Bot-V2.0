package md.basarabeasca.bot.infrastructure.converter.api;

import java.util.List;
import java.util.Map;
import md.basarabeasca.bot.domain.currency.ExchangeRate;

public interface ExchangeRateConverter {

  String toMessage(List<ExchangeRate> exchangeRate);

  List<String> toMessage(Map<String, List<ExchangeRate>> exchangeRates);

  String toMessage(ExchangeRate exchangeRate, String action, String currency);
}