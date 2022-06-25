package md.basarabeasca.bot.infrastructure.facade;

import java.util.List;
import java.util.Map;

public interface ExchangeRateFacade {

  String getBNMExchangeRates();

  List<String> getAllExchangeRates();

  Map<String, String> getBestPrivateBankExchangeRateFor(String currency, String action);
}
