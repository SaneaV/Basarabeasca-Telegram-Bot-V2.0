package md.basarabeasca.bot.action.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getCurrencyReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.service.ExchangeRatesService;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class PrivateBanksBestExchangeCommand implements Command {

  private static final String BEST_EXCHANGE = "Купить USD/Продать USD - Купить EUR/Продать EUR - "
      + "Купить RUB/Продать RUB - Купить RON/Продать RON - Купить UAH/Продать UAH";
  private static final String BUY = "Купить";
  private static final String MESSAGE_REGEX = "([a-яА-Я]+)\\s([A-Z]{3})";
  private static final String NOT_AVAILABLE_MESSAGE =
      "На данный момент ничего найти не можем. Возможные причины:\n"
          + "1. Все банки уже закрыты или ещё не открывались.\n"
          + "2. Указанная вами валюта, на данный момент, не продаётся/покупается в банках.";
  private static final String BEST_EXCHANGE_MESSAGE =
      "Лучше всего сегодня (%s) можно %s %s в городе "
          + "Бессарабка в банке %s:\n%s MDL\uD83C\uDDF2\uD83C\uDDE9 - 1 %s%s";
  private static final String EXCHANGE_RATE_ONLY_IN_MAIN_OFFICE = "*Курсы действительны только в "
      + "главном офисе банка и могут отличаться в его территориальных подразделениях.*";

  private static final Map<String, List<Double>> bankLocations = Map.of(
      "MICB", List.of(46.34025107589049, 28.96563477432835),
      "MAIB", List.of(46.33153125615052, 28.963369692079194),
      "FinComBank", List.of(46.3288529138939, 28.98279079088726));
  private static final Map<String, String> BANK_FULL_NAME = Map.of("MICB", "Moldindconbank",
      "MAIB", "MAIB", "FinComBank", "FinComBank");
  private static final Map<String, String> FLAGS = Map.of("USD", "\uD83C\uDDFA\uD83C\uDDF8",
      "EUR", "\uD83C\uDDEA\uD83C\uDDFA", "RUB", "\uD83C\uDDF7\uD83C\uDDFA",
      "RON", "\uD83C\uDDF7\uD83C\uDDF4", "UAH", "\uD83C\uDDFA\uD83C\uDDE6");

  private final ExchangeRatesService exchangeRatesService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return sendBestExchange(update.getMessage());
  }

  private List<? super PartialBotApiMethod<?>> sendBestExchange(Message message) {
    final String action = getPatternGroup(message.getText(), 1);
    final String currency = getPatternGroup(message.getText(), 2);
    final List<ExchangeRateDto> bestPrivateBankExchangeRates = exchangeRatesService
        .getBestPrivateBankExchangeRateFor(currency, action);
    if (isEmpty(bestPrivateBankExchangeRates)) {
      return singletonList(getSendMessageWithReplyKeyboardMarkup(message, NOT_AVAILABLE_MESSAGE,
          getCurrencyReplyKeyboardMarkup(action)));
    }
    final List<? super PartialBotApiMethod<?>> messages = new ArrayList<>();

    bestPrivateBankExchangeRates.forEach(er -> {
      final String priceByAction = getPriceByAction(action, er.getPurchase(), er.getSale());
      final SendMessage sendMessage = new SendMessage(message.getChatId().toString(),
          String.format(BEST_EXCHANGE_MESSAGE, LocalDate.now(), action.toLowerCase(), currency,
              BANK_FULL_NAME.get(er.getBankName()), priceByAction, currency, getFlag(currency)));
      final SendLocation sendLocation = getBankLocation(message, er.getBankName());

      messages.add(sendMessage);
      messages.add(sendLocation);
    });

    messages.add(getSendMessageWithReplyKeyboardMarkup(message, EXCHANGE_RATE_ONLY_IN_MAIN_OFFICE,
        getCurrencyReplyKeyboardMarkup(action)));

    return messages;
  }

  private String getPatternGroup(String message, int group) {
    final Pattern pattern = Pattern.compile(MESSAGE_REGEX);
    final Matcher matcher = pattern.matcher(message);

    if (matcher.find()) {
      return matcher.group(group);
    }
    throw new RuntimeException();
  }

  private String getPriceByAction(String action, String purchase, String sale) {
    if (BUY.equalsIgnoreCase(action)) {
      return purchase;
    }
    return sale;
  }

  private String getFlag(String currency) {
    return FLAGS.get(currency);
  }

  private SendLocation getBankLocation(Message message, String bankName) {
    final List<Double> coordinates = bankLocations.get(bankName);
    return new SendLocation(message.getChatId().toString(), coordinates.get(0), coordinates.get(1));
  }

  @Override
  public String getCommand() {
    return BEST_EXCHANGE;
  }
}
