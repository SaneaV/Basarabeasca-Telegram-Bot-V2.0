package md.basarabeasca.bot.infrastructure.converter.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import md.basarabeasca.bot.dao.domain.ExchangeRate;
import md.basarabeasca.bot.infrastructure.converter.ExchangeRateConverter;
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
  private static final String BEST_EXCHANGE_MESSAGE =
      "Лучше всего сегодня (%s) можно %s %s в городе "
          + "Бессарабка в банке %s:\n%s MDL\uD83C\uDDF2\uD83C\uDDE9 - 1 %s%s";
  private static final String BUY = "Купить";
  private static final Map<String, String> BANK_FULL_NAME = Map.of("MICB", "Moldindconbank",
      "MAIB", "MAIB", "FinComBank", "FinComBank");
  private static final Map<String, String> FLAGS = Map.of("USD", "\uD83C\uDDFA\uD83C\uDDF8",
      "EUR", "\uD83C\uDDEA\uD83C\uDDFA", "RUB", "\uD83C\uDDF7\uD83C\uDDFA",
      "RON", "\uD83C\uDDF7\uD83C\uDDF4", "UAH", "\uD83C\uDDFA\uD83C\uDDE6");

  @Override
  public String toMessage(List<ExchangeRate> exchangeRate) {
    final ExchangeRate USD = exchangeRate.get(0);
    final ExchangeRate EUR = exchangeRate.get(1);
    final ExchangeRate UAH = exchangeRate.get(2);
    final ExchangeRate RON = exchangeRate.get(3);
    final ExchangeRate RUB = exchangeRate.get(4);
    return String.format(EXCHANGE_RATES_RESPONSE, LocalDate.now(),
        USD.getCurrency(), USD.getPurchase(), EUR.getCurrency(), EUR.getPurchase(),
        UAH.getCurrency(), UAH.getPurchase(), RON.getCurrency(), RON.getPurchase(),
        RUB.getCurrency(), RUB.getPurchase());
  }

  @Override
  public String toMessage(ExchangeRate exchangeRate, String action, String currency) {
    final String priceByAction = getPriceByAction(action, exchangeRate.getPurchase(),
        exchangeRate.getSale());

    return String.format(BEST_EXCHANGE_MESSAGE, LocalDate.now(), action.toLowerCase(), currency,
        BANK_FULL_NAME.get(exchangeRate.getBankName()), priceByAction, currency, getFlag(currency));
  }

  private String getPriceByAction(String action, String purchase, String sale) {
    return BUY.equalsIgnoreCase(action) ? purchase : sale;
  }

  private String getFlag(String currency) {
    return FLAGS.get(currency);
  }

}
