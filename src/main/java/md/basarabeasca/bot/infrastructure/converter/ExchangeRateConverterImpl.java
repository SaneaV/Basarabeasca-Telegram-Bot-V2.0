package md.basarabeasca.bot.infrastructure.converter;

import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.BUY;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.EUR;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.FINCOMBANK;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.MAIB;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.MOLDINDCONBANK;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.RON;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.RUB;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.UAH;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.USD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.currency.ExchangeRate;
import md.basarabeasca.bot.infrastructure.converter.api.ExchangeRateConverter;
import md.basarabeasca.bot.infrastructure.validators.api.ExchangeRateValidator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateConverterImpl implements ExchangeRateConverter {

  //Flags
  private static final String USD_FLAG = "\uD83C\uDDFA\uD83C\uDDF8";
  private static final String EUR_FLAG = "\uD83C\uDDEA\uD83C\uDDFA";
  private static final String UAH_FLAG = "\uD83C\uDDFA\uD83C\uDDE6";
  private static final String RON_FLAG = "\uD83C\uDDF7\uD83C\uDDF4";
  private static final String RUB_FLAG = "\uD83C\uDDF7\uD83C\uDDFA";
  private static final String MDL_FLAG = "\uD83C\uDDF2\uD83C\uDDE9";
  private static final Map<String, String> FLAGS = new HashMap<>();

  //Banks
  private static final String BNM = "Banca Nationala a Moldovei";
  private static final Map<String, String> BANK_FULL_NAME = new HashMap<>();

  //Messages
  private static final String CURRENCY_VALUE = "Курс валют %s (%s):\n";
  private static final String BNM_EXCHANGE_RATES_RESPONSE = "%s %s - %s MDL\n";
  private static final String PRIVATE_EXCHANGE_RATES_RESPONSE = "%s Банк продаёт %s - %s MDL\n" +
      "%s Банк покупает %s - %s MDL\n";
  private static final String BEST_EXCHANGE_MESSAGE =
      "Лучше всего сегодня (%s) можно %s %s в городе Басарабяска в банке %s:"
          + "\n%s MDL" + MDL_FLAG + " - 1 %s%s";
  private static final String NOT_AVAILABLE_BANK = "Банк %s ещё/уже закрыт или ещё не обновил курс валют на сегодня";

  static {
    FLAGS.put(USD, USD_FLAG);
    FLAGS.put(EUR, EUR_FLAG);
    FLAGS.put(RUB, RUB_FLAG);
    FLAGS.put(RON, RON_FLAG);
    FLAGS.put(UAH, UAH_FLAG);

    BANK_FULL_NAME.put(MOLDINDCONBANK, "Moldindconbank");
    BANK_FULL_NAME.put(MAIB, MAIB);
    BANK_FULL_NAME.put(FINCOMBANK, FINCOMBANK);
  }

  private final ExchangeRateValidator validator;

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
    if (validator.isListOfExchangeRatesEmpty(exchangeRates)) {
      return String.format(NOT_AVAILABLE_BANK, bankName);
    }

    final StringBuilder message = new StringBuilder(
        String.format(CURRENCY_VALUE, bankName, LocalDate.now()));

    exchangeRates.stream()
        .filter(validator::isExchangeRateNotEmpty)
        .forEach(e -> {
          final String flag = FLAGS.get(e.getCurrency());
          final String currency = e.getCurrency();
          message.append(
              String.format(PRIVATE_EXCHANGE_RATES_RESPONSE, flag, currency, e.getPurchase(), flag,
                  currency, e.getSale()));
        });

    return message.toString();
  }

  private String getPriceByAction(String action, String purchase, String sale) {
    return BUY.equalsIgnoreCase(action) ? purchase : sale;
  }

  private String getFlag(String currency) {
    return FLAGS.get(currency);
  }
}
