package md.basarabeasca.bot.service;

import java.util.List;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;

public interface ExchangeRateService {

  String getBNMExchangeRates();

  List<ExchangeRateDto> getBestPrivateBankExchangeRateFor(String currency, String action);

  void updateExchangeRates();
}
