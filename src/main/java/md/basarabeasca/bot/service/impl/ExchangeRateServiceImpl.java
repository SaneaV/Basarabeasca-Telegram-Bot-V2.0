package md.basarabeasca.bot.service.impl;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.parser.ExchangeRateParser;
import md.basarabeasca.bot.repository.ExchangeRateRepository;
import md.basarabeasca.bot.repository.model.ExchangeRateJpa;
import md.basarabeasca.bot.service.ExchangeRateService;
import md.basarabeasca.bot.web.converter.ExchangeRateConverter;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

  private static final String PURCHASE = "Купить";
  private static final String DASH = "-";

  private final ExchangeRateRepository exchangeRateRepository;
  private final ExchangeRateParser exchangeRateParser;
  private final ExchangeRateConverter exchangeRateConverter;

  @Override
  public String getBNMExchangeRates() {
    final List<ExchangeRateJpa> exchangeRateJpas = exchangeRateRepository.findAllByOrderByIdAsc();

    if (isEmpty(exchangeRateJpas)) {
      return exchangeRateConverter.toString(saveExchangeRates());
    }

    return exchangeRateConverter.toString(exchangeRateJpas);
  }

  @Override
  public List<ExchangeRateDto> getBestPrivateBankExchangeRateFor(String currency, String action) {
    final List<ExchangeRate> allExchangeRates = exchangeRateParser.getPrivateBanksExchangeRates()
        .stream()
        .filter(ex -> !DASH.equalsIgnoreCase(ex.getPurchase()))
        .filter(ex -> currency.equalsIgnoreCase(ex.getCurrency()))
        .collect(toList());
    return getBestPrivateBankExchangeRate(allExchangeRates, action);
  }

  @Override
  public void updateExchangeRates() {
    final List<ExchangeRate> exchangeRates = exchangeRateParser.getBNMExchangeRates();

    exchangeRates.forEach(
        ex -> exchangeRateRepository.updateExchangeRate(ex.getCurrency(), ex.getPurchase()));
  }

  private List<ExchangeRateDto> getBestPrivateBankExchangeRate(List<ExchangeRate> allExchangeRates,
      String action) {
    if (isEmpty(allExchangeRates)) {
      return emptyList();
    }
    if (PURCHASE.equalsIgnoreCase(action)) {
      return allExchangeRates.stream()
          .collect(groupingBy(ExchangeRate::getPurchase, TreeMap::new, toList()))
          .firstEntry().getValue().stream()
          .map(exchangeRateConverter::toDto)
          .collect(toList());
    } else {
      return allExchangeRates.stream()
          .collect(groupingBy(ExchangeRate::getSale, TreeMap::new, toList()))
          .lastEntry().getValue().stream()
          .map(exchangeRateConverter::toDto)
          .collect(toList());
    }
  }

  private List<ExchangeRateJpa> saveExchangeRates() {
    final List<ExchangeRate> exchangeRates = exchangeRateParser.getBNMExchangeRates();

    final List<ExchangeRateJpa> exchangeRateJpas = exchangeRates.stream().map(
            exchangeRate -> exchangeRateRepository.save(ExchangeRateJpa.builder()
                .currency(exchangeRate.getCurrency())
                .value(exchangeRate.getPurchase())
                .build()))
        .collect(toList());

    return exchangeRateRepository.saveAll(exchangeRateJpas);
  }
}
