package md.basarabeasca.bot.infrastructure.parser.api;

import java.util.List;
import md.basarabeasca.bot.domain.currency.ExchangeRate;

public interface ExchangeRateParser {

  List<ExchangeRate> getBNMExchangeRates();

  List<ExchangeRate> getAllExchangeRates();

  List<ExchangeRate> getPrivateBanksExchangeRates();
}
