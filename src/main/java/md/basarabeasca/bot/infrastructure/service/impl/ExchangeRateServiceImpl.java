package md.basarabeasca.bot.infrastructure.service.impl;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.BUY;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.DASH;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.ExchangeRate;
import md.basarabeasca.bot.dao.mapper.ExchangeRateMapper;
import md.basarabeasca.bot.dao.repository.ExchangeRateRepository;
import md.basarabeasca.bot.dao.repository.model.ExchangeRateJpa;
import md.basarabeasca.bot.infrastructure.parser.ExchangeRateParser;
import md.basarabeasca.bot.infrastructure.service.ExchangeRateService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

  private final ExchangeRateRepository exchangeRateRepository;
  private final ExchangeRateParser exchangeRateParser;
  private final ExchangeRateMapper exchangeRateMapper;

  @Override
  public List<ExchangeRate> getBNMExchangeRates() {
    return getBNMExchangeRateJpas().stream()
        .map(exchangeRateMapper::toEntity)
        .collect(toList());
  }

  @Override
  public List<ExchangeRate> getAllExchangeRates() {
    return exchangeRateParser.getAllExchangeRates();
  }

  @Override
  public List<ExchangeRate> getBestPrivateBankExchangeRateFor(String currency, String action) {
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

  private List<ExchangeRateJpa> getBNMExchangeRateJpas() {
    final List<ExchangeRateJpa> exchangeRateJpas = exchangeRateRepository.findAllByOrderByIdAsc();

    if (isEmpty(exchangeRateJpas)) {
      return saveExchangeRates();
    }

    return exchangeRateJpas;
  }

  private List<ExchangeRate> getBestPrivateBankExchangeRate(List<ExchangeRate> allExchangeRates,
      String action) {
    if (isEmpty(allExchangeRates)) {
      return emptyList();
    }
    if (BUY.equalsIgnoreCase(action)) {
      return new ArrayList<>(allExchangeRates.stream()
          .collect(groupingBy(ExchangeRate::getPurchase, TreeMap::new, toList()))
          .firstEntry().getValue());
    } else {
      return new ArrayList<>(allExchangeRates.stream()
          .collect(groupingBy(ExchangeRate::getSale, TreeMap::new, toList()))
          .lastEntry().getValue());
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
