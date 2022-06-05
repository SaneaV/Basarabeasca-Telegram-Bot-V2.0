package md.basarabeasca.bot.facade;

import java.util.List;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;

public interface ExchangeRateFacade {

  String getBNMExchangeRates();

  List<ExchangeRateDto> getBestPrivateBankExchangeRateFor(String currency, String action);
}
