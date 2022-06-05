package md.basarabeasca.bot.web.converter.impl;

import java.time.LocalDate;
import java.util.List;
import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.repository.model.ExchangeRateJpa;
import md.basarabeasca.bot.web.converter.ExchangeRateConverter;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateConverterImpl implements ExchangeRateConverter {

  private static final String EXCHANGE_RATES_RESPONSE =
      "Курс валют Banca Nationala a Moldovei (%s):\n" +
          "\uD83C\uDDFA\uD83C\uDDF8 %s - %s MDL\n" +
          "\uD83C\uDDEA\uD83C\uDDFA %s - %s MDL\n" +
          "\uD83C\uDDFA\uD83C\uDDE6 %s - %s MDL\n" +
          "\uD83C\uDDF7\uD83C\uDDF4 %s - %s MDL\n" +
          "\uD83C\uDDF7\uD83C\uDDFA %s - %s MDL\n";


  @Override
  public String toString(List<ExchangeRateJpa> exchangeRates) {
    return String.format(EXCHANGE_RATES_RESPONSE,
        LocalDate.now(),
        exchangeRates.get(0).getCurrency(), exchangeRates.get(0).getValue(),
        exchangeRates.get(1).getCurrency(), exchangeRates.get(1).getValue(),
        exchangeRates.get(2).getCurrency(), exchangeRates.get(2).getValue(),
        exchangeRates.get(3).getCurrency(), exchangeRates.get(3).getValue(),
        exchangeRates.get(4).getCurrency(), exchangeRates.get(4).getValue());
  }

  @Override
  public ExchangeRateDto toDto(ExchangeRate exchangeRate) {
    return ExchangeRateDto.builder()
        .bankName(exchangeRate.getBankName())
        .currency(exchangeRate.getCurrency())
        .purchase(exchangeRate.getPurchase())
        .sale(exchangeRate.getSale())
        .build();
  }
}
