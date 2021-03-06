package md.basarabeasca.bot.infrastructure.parser;

import java.util.List;
import md.basarabeasca.bot.dao.domain.ExchangeRate;

public interface ExchangeRateParser {

  List<ExchangeRate> getBNMExchangeRates();

  List<ExchangeRate> getAllExchangeRates();

  List<ExchangeRate> getPrivateBanksExchangeRates();
}
