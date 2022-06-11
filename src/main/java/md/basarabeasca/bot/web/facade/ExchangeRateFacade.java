package md.basarabeasca.bot.web.facade;

import java.util.List;
import md.basarabeasca.bot.domain.ExchangeRate;

public interface ExchangeRateFacade {

  String getBNMExchangeRates();

  List<ExchangeRate> getBestPrivateBankExchangeRateFor(String currency, String action);
}