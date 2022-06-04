package md.basarabeasca.bot.service.impl;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.parser.ExchangeRatesParser;
import md.basarabeasca.bot.repository.ExchangeRatesRepository;
import md.basarabeasca.bot.repository.model.ExchangeRateJpa;
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
    final List<ExchangeRateJpa> exchangeRateJpas = exchangeRatesRepository.findAllByOrderByIdAsc();

    if (isEmpty(exchangeRateJpas)) {
      return saveExchangeRates().stream()
          .map(this::convertToDTO)
          .collect(toList());
    }

    return exchangeRateJpas.stream()
        .map(this::convertToDTO)
        .collect(toList());
  }

  @Override
  public List<ExchangeRateDto> getBestPrivateBankExchangeRateFor(String currency, String action) {
    final List<ExchangeRate> allExchangeRates = exchangeRatesParser.getPrivateBanksExchangeRates()
        .stream()
        .filter(ex -> !DASH.equalsIgnoreCase(ex.getPurchase()))
        .filter(ex -> currency.equalsIgnoreCase(ex.getCurrency()))
        .collect(toList());
    return getBestPrivateBankExchangeRate(allExchangeRates, action);
  }

  @Override
  public void updateExchangeRates() {
    final List<ExchangeRate> exchangeRates = exchangeRatesParser.getBNMExchangeRates();

    exchangeRates.forEach(
        ex -> exchangeRatesRepository.updateExchangeRate(ex.getCurrency(), ex.getPurchase()));
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
          .map(this::convertToDTO)
          .collect(toList());
    } else {
      return allExchangeRates.stream()
          .collect(groupingBy(ExchangeRate::getSale, TreeMap::new, toList()))
          .lastEntry().getValue().stream()
          .map(this::convertToDTO)
          .collect(toList());
    }
  }

  private List<ExchangeRateJpa> saveExchangeRates() {
    final List<ExchangeRate> exchangeRates = exchangeRatesParser.getBNMExchangeRates();

    final List<ExchangeRateJpa> exchangeRateJpas = exchangeRates.stream().map(
            exchangeRate -> exchangeRatesRepository.save(ExchangeRateJpa.builder()
                .currency(exchangeRate.getCurrency())
                .value(exchangeRate.getPurchase())
                .build()))
        .collect(toList());

    return exchangeRatesRepository.saveAll(exchangeRateJpas);
  }

  public ExchangeRateDto convertToDTO(ExchangeRateJpa exchangeRateJpa) {
    return ExchangeRateDto.builder()
        .purchase(exchangeRateJpa.getValue())
        .currency(exchangeRateJpa.getCurrency())
        .build();
  }

  public ExchangeRateDto convertToDTO(ExchangeRate exchangeRate) {
    return ExchangeRateDto.builder()
        .bankName(exchangeRate.getBankName())
        .currency(exchangeRate.getCurrency())
        .purchase(exchangeRate.getPurchase())
        .sale(exchangeRate.getSale())
        .build();
  }
}
