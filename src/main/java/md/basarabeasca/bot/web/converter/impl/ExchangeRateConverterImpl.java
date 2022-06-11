package md.basarabeasca.bot.web.converter.impl;

import java.time.LocalDate;
import java.util.List;
import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.web.converter.ExchangeRateConverter;
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
  public String toMessage(List<ExchangeRate> exchangeRate) {
    return String.format(EXCHANGE_RATES_RESPONSE,
        LocalDate.now(),
        exchangeRate.get(0).getCurrency(), exchangeRate.get(0).getPurchase(),
        exchangeRate.get(1).getCurrency(), exchangeRate.get(1).getPurchase(),
        exchangeRate.get(2).getCurrency(), exchangeRate.get(2).getPurchase(),
        exchangeRate.get(3).getCurrency(), exchangeRate.get(3).getPurchase(),
        exchangeRate.get(4).getCurrency(), exchangeRate.get(4).getPurchase());
  }
}
