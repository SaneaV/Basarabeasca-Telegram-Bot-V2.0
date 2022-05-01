package md.basarabeasca.bot.parser;

import java.util.List;
import md.basarabeasca.bot.domain.Rate;

public interface ExchangeRatesParser {

  List<Rate> getBNMExchangeRates();

  List<Rate> getPrivateBanksExchangeRates();
}
