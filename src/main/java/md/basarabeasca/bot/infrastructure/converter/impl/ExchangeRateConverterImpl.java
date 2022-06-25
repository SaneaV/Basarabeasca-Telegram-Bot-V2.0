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
  private static final Map<String, String> BANK_FULL_NAME = Map.of(MOLDINDCONBANK, "Moldindconbank",
      MAIB, MAIB, FINCOMBANK, FINCOMBANK);

  //Messages
  private static final String BNM_EXCHANGE_RATES_RESPONSE =
      "Курс валют Banca Nationala a Moldovei (%s):\n" +
          USD_FLAG + " %s - %s MDL\n" +
          EUR_FLAG + " %s - %s MDL\n" +
          UAH_FLAG + " %s - %s MDL\n" +
          RON_FLAG + " %s - %s MDL\n" +
          RUB_FLAG + " %s - %s MDL\n";
  private static final String PRIVATE_EXCHANGE_RATES_RESPONSE =
      "Курс валют %s (%s):\n" +
          USD_FLAG + " Банк продаёт %s - %s MDL\n" +
          USD_FLAG + " Банк покупает %s - %s MDL\n" +
          EUR_FLAG + " Банк продаёт %s - %s MDL\n" +
          EUR_FLAG + " Банк покупает %s - %s MDL\n" +
          UAH_FLAG + " Банк продаёт %s - %s MDL\n" +
          UAH_FLAG + " Банк покупает %s - %s MDL\n" +
          RON_FLAG + " Банк продаёт %s - %s MDL\n" +
          RON_FLAG + " Банк покупает %s - %s MDL\n" +
          RUB_FLAG + " Банк продаёт %s - %s MDL\n" +
          RUB_FLAG + " Банк покупает %s - %s MDL\n";
  private static final String BEST_EXCHANGE_MESSAGE =
      "Лучше всего сегодня (%s) можно %s %s в городе Басарабяска в банке %s:\n%s MDL" + MDL_FLAG
          + " - 1 %s%s";
  private static final String NOT_AVAILABLE_BANK = "Банк %s ещё/уже закрыт или ещё не обновил курс валют на сегодня";

  //Other
  private static final String BUY = "Купить";
  private static final String DASH = "-";

  @Override
  public String toMessage(List<ExchangeRate> exchangeRate) {
    final ExchangeRate USD = exchangeRate.get(0);
    final ExchangeRate EUR = exchangeRate.get(1);
    final ExchangeRate UAH = exchangeRate.get(2);
    final ExchangeRate RON = exchangeRate.get(3);
    final ExchangeRate RUB = exchangeRate.get(4);

    return String.format(BNM_EXCHANGE_RATES_RESPONSE, LocalDate.now(),
        USD.getCurrency(), USD.getPurchase(), EUR.getCurrency(), EUR.getPurchase(),
        UAH.getCurrency(), UAH.getPurchase(), RON.getCurrency(), RON.getPurchase(),
        RUB.getCurrency(), RUB.getPurchase());
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

    final ExchangeRate USD = exchangeRates.get(0);
    final ExchangeRate EUR = exchangeRates.get(1);
    final ExchangeRate RUB = exchangeRates.get(2);
    final ExchangeRate RON = exchangeRates.get(3);
    final ExchangeRate UAH = exchangeRates.get(4);

    return String.format(PRIVATE_EXCHANGE_RATES_RESPONSE, bankName, LocalDate.now(),
        USD.getCurrency(), USD.getPurchase(), USD.getCurrency(), USD.getSale(),
        EUR.getCurrency(), EUR.getPurchase(), EUR.getCurrency(), EUR.getSale(),
        UAH.getCurrency(), UAH.getPurchase(), UAH.getCurrency(), UAH.getSale(),
        RON.getCurrency(), RON.getPurchase(), RON.getCurrency(), RON.getSale(),
        RUB.getCurrency(), RUB.getPurchase(), RUB.getCurrency(), RUB.getSale()
    );
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
