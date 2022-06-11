package md.basarabeasca.bot.infrastructure.service;

import java.util.List;
import md.basarabeasca.bot.domain.ExchangeRate;

public interface ExchangeRateService {

  List<ExchangeRate> getBNMExchangeRates();

  List<ExchangeRate> getBestPrivateBankExchangeRateFor(String currency, String action);

  void updateExchangeRates();
}
