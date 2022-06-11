package md.basarabeasca.bot.web.facade.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.web.facade.ExchangeRateFacade;
import md.basarabeasca.bot.infrastructure.service.ExchangeRateService;
import md.basarabeasca.bot.infrastructure.service.UpdateDateService;
import md.basarabeasca.bot.web.converter.ExchangeRateConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateFacadeImpl implements ExchangeRateFacade {

  private static final String ZONE_EUROPE_CHISINAU = "Europe/Chisinau";

  private final UpdateDateService updateDateService;
  private final ExchangeRateService exchangeRateService;
  private final ExchangeRateConverter exchangeRateConverter;

  @Override
  public String getBNMExchangeRates() {
    if (!updateDateService.getDate().isEqual(LocalDate.now(ZoneId.of(ZONE_EUROPE_CHISINAU)))) {
      updateDateService.updateDate();
      exchangeRateService.updateExchangeRates();
    }

    final List<ExchangeRate> bnmExchangeRates = exchangeRateService.getBNMExchangeRates();
    return exchangeRateConverter.toMessage(bnmExchangeRates);
  }

  //TODO: remove all side logic from ExchangeRateCommand
  @Override
  public List<ExchangeRate> getBestPrivateBankExchangeRateFor(String currency, String action) {
    return exchangeRateService.getBestPrivateBankExchangeRateFor(currency, action);
  }
}
