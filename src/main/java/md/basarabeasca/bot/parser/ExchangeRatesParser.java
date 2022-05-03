package md.basarabeasca.bot.parser;

import java.util.List;
import md.basarabeasca.bot.domain.ExchangeRate;

public interface ExchangeRatesParser {

  List<ExchangeRate> getBNMExchangeRates();

  List<ExchangeRate> getPrivateBanksExchangeRates();
}
