package md.basarabeasca.bot.infrastructure.converter.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import md.basarabeasca.bot.dao.domain.ExchangeRate;
import md.basarabeasca.bot.infrastructure.converter.ExchangeRateConverter;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateConverterImpl implements ExchangeRateConverter {

  //Flags
  private static final String USD_FLAG = "\uD83C\uDDFA\uD83C\uDDF8";
  private static final String EUR_FLAG = "\uD83C\uDDEA\uD83C\uDDFA";
  private static final String UAH_FLAG = "\uD83C\uDDFA\uD83C\uDDE6";
  private static final String RON_FLAG = "\uD83C\uDDF7\uD83C\uDDF4";
  private static final String RUB_FLAG = "\uD83C\uDDF7\uD83C\uDDFA";
  private static final String MDL_FLAG = "\uD83C\uDDF2\uD83C\uDDE9";
  private static final Map<String, String> FLAGS = Map.of("USD", USD_FLAG,
      "EUR", EUR_FLAG, "RUB", RUB_FLAG, "RON", RON_FLAG, "UAH", UAH_FLAG);

  //Banks
  private static final String MOLDINDCONBANK = "MICB";
  private static final String MAIB = "MAIB";
  private static final String FINCOMBANK = "FinComBank";
  private static final String BNM = "Banca Nationala a Moldovei";
  private static final Map<String, String> BANK_FULL_NAME = Map.of(MOLDINDCONBANK, "Moldindconbank",
      MAIB, MAIB, FINCOMBANK, FINCOMBANK);

  //Messages
  private static final String CURRENCY_VALUE = "Курс валют %s (%s):\n";
  private static final String BNM_EXCHANGE_RATES_RESPONSE = "%s %s - %s MDL\n";
  private static final String PRIVATE_EXCHANGE_RATES_RESPONSE =
      "%s Банк продаёт %s - %s MDL\n" +
          "%s Банк покупает %s - %s MDL\n";
  private static final String BEST_EXCHANGE_MESSAGE =
      "Лучше всего сегодня (%s) можно %s %s в городе Басарабяска в банке %s:"
          + "\n%s MDL" + MDL_FLAG + " - 1 %s%s";
  private static final String NOT_AVAILABLE_BANK = "Банк %s ещё/уже закрыт или ещё не обновил курс валют на сегодня";

  //Other
  private static final String BUY = "Купить";
  private static final String DASH = "-";

  @Override
  public String toMessage(List<ExchangeRate> exchangeRate) {
    final StringBuilder message = new StringBuilder(
        String.format(CURRENCY_VALUE, BNM, LocalDate.now()));

    exchangeRate.forEach(e -> message.append(
        String.format(BNM_EXCHANGE_RATES_RESPONSE, FLAGS.get(e.getCurrency()), e.getCurrency(),
            e.getPurchase())));

    return message.toString();
  }

  @Override
  public List<String> toMessage(Map<String, List<ExchangeRate>> exchangeRates) {
    final List<String> messages = new ArrayList<>();
    exchangeRates.forEach(
        (k, v) -> messages.add(populatePrivateExchangeMessage(BANK_FULL_NAME.get(k), v)));

    return messages;
  }

  @Override
  public String toMessage(ExchangeRate exchangeRate, String action, String currency) {
    final String priceByAction = getPriceByAction(action, exchangeRate.getPurchase(),
        exchangeRate.getSale());

    return String.format(BEST_EXCHANGE_MESSAGE, LocalDate.now(), action.toLowerCase(), currency,
        BANK_FULL_NAME.get(exchangeRate.getBankName()), priceByAction, currency, getFlag(currency));
  }

  private String populatePrivateExchangeMessage(String bankName, List<ExchangeRate> exchangeRates) {
    if (isListOfExchangeRatesEmpty(exchangeRates)) {
      return String.format(NOT_AVAILABLE_BANK, bankName);
    }

    final StringBuilder message = new StringBuilder(
        String.format(CURRENCY_VALUE, bankName, LocalDate.now()));

    exchangeRates.stream()
        .filter(e -> !DASH.equals(e.getPurchase()) && !DASH.equals(e.getSale()))
        .forEach(e -> {
          final String flag = FLAGS.get(e.getCurrency());
          final String currency = e.getCurrency();
          message.append(
              String.format(PRIVATE_EXCHANGE_RATES_RESPONSE, flag, currency, e.getPurchase(), flag,
                  currency, e.getSale()));
        });

    return message.toString();
  }

  private boolean isListOfExchangeRatesEmpty(List<ExchangeRate> exchangeRates) {
    final List<ExchangeRate> filteredExchangeRates = exchangeRates.stream()
        .filter(e -> DASH.equals(e.getPurchase()) && DASH.equals(e.getSale()))
        .collect(Collectors.toList());

    return filteredExchangeRates.size() == 0;
  }

  private String getPriceByAction(String action, String purchase, String sale) {
    return BUY.equalsIgnoreCase(action) ? purchase : sale;
  }

  private String getFlag(String currency) {
    return FLAGS.get(currency);
  }
}
