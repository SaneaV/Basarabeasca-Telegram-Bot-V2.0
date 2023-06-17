package md.basarabeasca.bot.infrastructure.validators;

import static java.util.stream.Collectors.toList;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.DASH;

import java.util.List;
import md.basarabeasca.bot.domain.currency.ExchangeRate;
import md.basarabeasca.bot.infrastructure.validators.api.ExchangeRateValidator;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateValidatorImpl implements ExchangeRateValidator {

  @Override
  public boolean isListOfExchangeRatesEmpty(List<ExchangeRate> exchangeRates) {
    final List<ExchangeRate> filteredExchangeRates = exchangeRates.stream()
        .filter(this::isExchangeRateEmpty)
        .collect(toList());

    return filteredExchangeRates.size() == 0;
  }

  @Override
  public boolean isExchangeRateNotEmpty(ExchangeRate exchangeRate) {
    return !isExchangeRateEmpty(exchangeRate);
  }

  private boolean isExchangeRateEmpty(ExchangeRate exchangeRate) {
    return DASH.equals(exchangeRate.getPurchase()) && DASH.equals(exchangeRate.getSale());
  }
}
