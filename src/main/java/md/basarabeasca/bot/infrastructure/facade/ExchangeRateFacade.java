package md.basarabeasca.bot.infrastructure.facade;

import java.util.Map;

public interface ExchangeRateFacade {

  String getBNMExchangeRates();

  Map<String, String> getBestPrivateBankExchangeRateFor(String currency, String action);
}
