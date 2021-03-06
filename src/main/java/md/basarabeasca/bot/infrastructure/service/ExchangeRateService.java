package md.basarabeasca.bot.infrastructure.service;

import java.util.List;
import md.basarabeasca.bot.dao.domain.ExchangeRate;

public interface ExchangeRateService {

  List<ExchangeRate> getBNMExchangeRates();

  List<ExchangeRate> getAllExchangeRates();

  List<ExchangeRate> getBestPrivateBankExchangeRateFor(String currency, String action);

  void updateExchangeRates();
}
