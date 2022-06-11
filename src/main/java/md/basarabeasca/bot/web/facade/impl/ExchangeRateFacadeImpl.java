package md.basarabeasca.bot.web.facade.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.ExchangeRate;
import md.basarabeasca.bot.infrastructure.service.ExchangeRateService;
import md.basarabeasca.bot.infrastructure.service.LocationService;
import md.basarabeasca.bot.infrastructure.service.UpdateDateService;
import md.basarabeasca.bot.web.converter.ExchangeRateConverter;
import md.basarabeasca.bot.web.facade.ExchangeRateFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateFacadeImpl implements ExchangeRateFacade {

  private static final String ZONE_EUROPE_CHISINAU = "Europe/Chisinau";

  private final UpdateDateService updateDateService;
  private final ExchangeRateService exchangeRateService;
  private final ExchangeRateConverter exchangeRateConverter;
  private final LocationService locationService;

  @Override
  public String getBNMExchangeRates() {
    if (!updateDateService.getDate().isEqual(LocalDate.now(ZoneId.of(ZONE_EUROPE_CHISINAU)))) {
      updateDateService.updateDate();
      exchangeRateService.updateExchangeRates();
    }

    final List<ExchangeRate> bnmExchangeRates = exchangeRateService.getBNMExchangeRates();
    return exchangeRateConverter.toMessage(bnmExchangeRates);
  }

  @Override
  public List<ExchangeRate> getBestPrivateBankExchangeRateFor(String currency, String action) {
    return exchangeRateService.getBestPrivateBankExchangeRateFor(currency, action);
  }
}
