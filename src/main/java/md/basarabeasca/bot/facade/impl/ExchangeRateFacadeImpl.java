package md.basarabeasca.bot.facade.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.facade.ExchangeRateFacade;
import md.basarabeasca.bot.service.ExchangeRateService;
import md.basarabeasca.bot.service.UpdateDateService;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateFacadeImpl implements ExchangeRateFacade {

  private static final String ZONE_EUROPE_CHISINAU = "Europe/Chisinau";

  private final UpdateDateService updateDateService;
  private final ExchangeRateService exchangeRateService;

  @Override
  public String getBNMExchangeRates() {
    if (!updateDateService.getDate().isEqual(LocalDate.now(ZoneId.of(ZONE_EUROPE_CHISINAU)))) {
      updateDateService.updateDate();
      exchangeRateService.updateExchangeRates();
    }

    return exchangeRateService.getBNMExchangeRates();
  }

  //TODO: remove all side logic from ExchangeRateCommand
  @Override
  public List<ExchangeRateDto> getBestPrivateBankExchangeRateFor(String currency, String action) {
    return exchangeRateService.getBestPrivateBankExchangeRateFor(currency, action);
  }
}
