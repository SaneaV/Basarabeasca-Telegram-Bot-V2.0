package md.basarabeasca.bot.infrastructure.validators.api;

import java.util.List;
import md.basarabeasca.bot.domain.currency.ExchangeRate;

public interface ExchangeRateValidator {

  boolean isListOfExchangeRatesEmpty(List<ExchangeRate> exchangeRates);

  boolean isExchangeRateNotEmpty(ExchangeRate exchangeRates);
}
