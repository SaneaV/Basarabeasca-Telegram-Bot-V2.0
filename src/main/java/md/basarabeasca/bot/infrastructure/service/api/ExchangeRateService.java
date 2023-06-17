package md.basarabeasca.bot.infrastructure.service.api;

import java.util.List;
import md.basarabeasca.bot.domain.currency.ExchangeRate;

public interface ExchangeRateService {

  List<ExchangeRate> getBNMExchangeRates();

  List<ExchangeRate> getAllExchangeRates();

  List<ExchangeRate> getBestPrivateBankExchangeRateFor(String currency, String action);

  void updateExchangeRates();
}
