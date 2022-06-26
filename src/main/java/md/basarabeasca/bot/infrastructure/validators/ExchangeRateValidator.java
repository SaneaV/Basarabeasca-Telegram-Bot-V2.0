package md.basarabeasca.bot.infrastructure.validators;

import java.util.List;
import md.basarabeasca.bot.dao.domain.ExchangeRate;

public interface ExchangeRateValidator {

  boolean isListOfExchangeRatesEmpty(List<ExchangeRate> exchangeRates);

  boolean isExchangeRateNotEmpty(ExchangeRate exchangeRates);
}
