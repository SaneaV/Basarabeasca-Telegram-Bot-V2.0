package md.basarabeasca.bot.service.impl;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.Rate;
import md.basarabeasca.bot.parser.ExchangeRatesParser;
import md.basarabeasca.bot.repository.ExchangeRatesRepository;
import md.basarabeasca.bot.repository.model.ExchangeRate;
import md.basarabeasca.bot.service.ExchangeRatesService;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

  private static final String PURCHASE = "Купить";
  private static final String DASH = "-";

  private final ExchangeRatesRepository exchangeRatesRepository;
  private final ExchangeRatesParser exchangeRatesParser;

  @Override
  public List<ExchangeRateDto> getBNMExchangeRates() {
    final List<ExchangeRate> exchangeRates = exchangeRatesRepository.findAll();

    if (isEmpty(exchangeRates)) {
      return saveExchangeRates().stream()
          .map(this::convertToDTO)
          .collect(toList());
    }

    return exchangeRates.stream()
        .map(this::convertToDTO)
        .collect(toList());
  }

  @Override
  public List<ExchangeRateDto> getBestPrivateBankExchangeRateFor(String currency, String action) {
    final List<Rate> allRates = exchangeRatesParser.getPrivateBanksExchangeRates().stream()
        .filter(ex -> currency.equalsIgnoreCase(ex.getCurrency()))
        .collect(toList());
    return getBestPrivateBankExchangeRate(allRates, action);
  }

  @Override
  public void updateExchangeRates() {
    final List<Rate> rates = exchangeRatesParser.getBNMExchangeRates();

    rates.forEach(
        ex -> exchangeRatesRepository.updateExchangeRate(ex.getCurrency(), ex.getPurchase()));
  }

  private List<ExchangeRateDto> getBestPrivateBankExchangeRate(List<Rate> allRates, String action) {
    if (isEmpty(allRates)) {
      return emptyList();
    }
    if (PURCHASE.equalsIgnoreCase(action)) {
      return allRates.stream()
          .collect(groupingBy(Rate::getPurchase, TreeMap::new, toList()))
          .firstEntry().getValue().stream()
          .filter(ex -> !DASH.equalsIgnoreCase(ex.getPurchase()))
          .map(this::convertToDTO)
          .collect(toList());
    } else {
      return allRates.stream()
          .collect(groupingBy(Rate::getSale, TreeMap::new, toList()))
          .lastEntry().getValue().stream()
          .filter(ex -> !DASH.equalsIgnoreCase(ex.getSale()))
          .map(this::convertToDTO)
          .collect(toList());
    }
  }

  private List<ExchangeRate> saveExchangeRates() {
    final List<Rate> rates = exchangeRatesParser.getBNMExchangeRates();

    final List<ExchangeRate> exchangeRates = rates.stream().map(
            rate -> exchangeRatesRepository.save(ExchangeRate.builder()
                .currency(rate.getCurrency())
                .value(rate.getPurchase())
                .build()))
        .collect(toList());

    return exchangeRatesRepository.saveAll(exchangeRates);
  }

  public ExchangeRateDto convertToDTO(ExchangeRate exchangeRate) {
    return ExchangeRateDto.builder()
        .purchase(exchangeRate.getValue())
        .currency(exchangeRate.getCurrency())
        .build();
  }

  public ExchangeRateDto convertToDTO(Rate rate) {
    return ExchangeRateDto.builder()
        .bankName(rate.getBankName())
        .currency(rate.getCurrency())
        .purchase(rate.getPurchase())
        .sale(rate.getSale())
        .build();
  }
}
