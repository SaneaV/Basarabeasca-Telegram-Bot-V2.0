package md.basarabeasca.bot.infrastructure.service;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static md.basarabeasca.bot.infrastructure.config.EhcacheConfig.ALL_EXCHANGE_RATE;
import static md.basarabeasca.bot.infrastructure.config.EhcacheConfig.EXCHANGE_RATE_BNM;
import static md.basarabeasca.bot.infrastructure.config.EhcacheConfig.J_CACHE_CACHE_MANAGER;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.BUY;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.DASH;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.currency.ExchangeRate;
import md.basarabeasca.bot.domain.currency.ExchangeRateMapper;
import md.basarabeasca.bot.domain.currency.ExchangeRateRepository;
import md.basarabeasca.bot.infrastructure.jpa.ExchangeRateJpa;
import md.basarabeasca.bot.infrastructure.parser.api.ExchangeRateParser;
import md.basarabeasca.bot.infrastructure.service.api.ExchangeRateService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

  private final ExchangeRateRepository exchangeRateRepository;
  private final ExchangeRateParser exchangeRateParser;
  private final ExchangeRateMapper exchangeRateMapper;

  @Override
  @Cacheable(value = EXCHANGE_RATE_BNM, cacheManager = J_CACHE_CACHE_MANAGER)
  public List<ExchangeRate> getBNMExchangeRates() {
    return getBNMExchangeRateJpas().stream()
        .map(exchangeRateMapper::toEntity)
        .collect(toList());
  }

  @Override
  @Cacheable(value = ALL_EXCHANGE_RATE, cacheManager = J_CACHE_CACHE_MANAGER)
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
            exchangeRate -> ExchangeRateJpa.builder()
                .currency(exchangeRate.getCurrency())
                .value(exchangeRate.getPurchase())
                .build())
        .collect(toList());

    return exchangeRateRepository.saveAll(exchangeRateJpas);
  }
}
