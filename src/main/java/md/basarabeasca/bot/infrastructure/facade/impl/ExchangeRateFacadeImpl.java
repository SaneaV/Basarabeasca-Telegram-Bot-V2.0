package md.basarabeasca.bot.infrastructure.facade.impl;

import static java.util.Collections.emptyList;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.FINCOMBANK;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.MAIB;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.MOLDINDCONBANK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.ExchangeRate;
import md.basarabeasca.bot.infrastructure.converter.ExchangeRateConverter;
import md.basarabeasca.bot.infrastructure.facade.ExchangeRateFacade;
import md.basarabeasca.bot.infrastructure.service.ExchangeRateService;
import md.basarabeasca.bot.infrastructure.service.UpdateDateService;
import md.basarabeasca.bot.infrastructure.validators.ExchangeRateValidator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateFacadeImpl implements ExchangeRateFacade {

  private final UpdateDateService updateDateService;
  private final ExchangeRateService exchangeRateService;
  private final ExchangeRateConverter exchangeRateConverter;
  private final ExchangeRateValidator exchangeRateValidator;

  @Override
  public String getBNMExchangeRates() {
    updateDateService.checkUpToDateInformation();

    final List<ExchangeRate> bnmExchangeRates = exchangeRateService.getBNMExchangeRates();
    return exchangeRateConverter.toMessage(bnmExchangeRates);
  }

  @Override
  public List<String> getAllExchangeRates() {
    final List<ExchangeRate> allExchangeRates = exchangeRateService.getAllExchangeRates();

    if (exchangeRateValidator.isListOfExchangeRatesEmpty(allExchangeRates)) {
      return emptyList();
    }

    final Map<String, List<ExchangeRate>> banksAndExchangeRates = Map.of(MOLDINDCONBANK,
        new ArrayList<>(), MAIB, new ArrayList<>(), FINCOMBANK, new ArrayList<>());

    allExchangeRates.forEach(e -> banksAndExchangeRates.get(e.getBankName()).add(e));
    return exchangeRateConverter.toMessage(banksAndExchangeRates);
  }

  @Override
  public Map<String, String> getBestPrivateBankExchangeRateFor(String currency, String action) {
    final List<ExchangeRate> exchangeRates = exchangeRateService.getBestPrivateBankExchangeRateFor(
        currency, action);

    final Map<String, String> exchangeRatesMessages = new HashMap<>();
    exchangeRates.forEach(
        e -> {
          //TODO: Remove currency, there is no need in this parameter cause we have currency in each exchange rate and in was already filtered on service layer
          final String message = exchangeRateConverter.toMessage(e, action, currency);
          exchangeRatesMessages.put(e.getBankName(), message);
        }
    );
    return exchangeRatesMessages;
  }
}
