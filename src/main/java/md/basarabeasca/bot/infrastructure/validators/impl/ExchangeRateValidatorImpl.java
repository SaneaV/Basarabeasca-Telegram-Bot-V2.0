package md.basarabeasca.bot.infrastructure.validators.impl;

import java.util.List;
import java.util.stream.Collectors;
import md.basarabeasca.bot.dao.domain.ExchangeRate;
import md.basarabeasca.bot.infrastructure.validators.ExchangeRateValidator;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateValidatorImpl implements ExchangeRateValidator {

  private static final String DASH = "-";

  @Override
  public boolean isListOfExchangeRatesEmpty(List<ExchangeRate> exchangeRates) {
    final List<ExchangeRate> filteredExchangeRates = exchangeRates.stream()
        .filter(e -> DASH.equals(e.getPurchase()) && DASH.equals(e.getSale()))
        .collect(Collectors.toList());

    return filteredExchangeRates.size() == 0;
  }

  @Override
  public boolean isExchangeRateNotEmpty(ExchangeRate exchangeRate) {
    return !DASH.equals(exchangeRate.getPurchase()) && !DASH.equals(exchangeRate.getSale());
  }
}
