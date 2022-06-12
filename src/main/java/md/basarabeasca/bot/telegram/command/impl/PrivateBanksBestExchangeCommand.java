package md.basarabeasca.bot.telegram.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getCurrencyReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import md.basarabeasca.bot.dao.domain.Location;
import md.basarabeasca.bot.infrastructure.service.LocationService;
import md.basarabeasca.bot.telegram.command.Command;
import md.basarabeasca.bot.infrastructure.facade.ExchangeRateFacade;
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
  private static final String MESSAGE_REGEX = "([a-яА-Я]+)\\s([A-Z]{3})";
  private static final String NOT_AVAILABLE_MESSAGE =
      "На данный момент ничего найти не можем. Возможные причины:\n"
          + "1. Все банки уже закрыты или ещё не открывались.\n"
          + "2. Указанная вами валюта, на данный момент, не продаётся/покупается в банках.";
  private static final String EXCHANGE_RATE_ONLY_IN_MAIN_OFFICE = "*Курсы действительны только в "
      + "главном офисе банка и могут отличаться в его территориальных подразделениях.*";

  private final ExchangeRateFacade exchangeRateFacade;
  private final LocationService locationService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return sendBestExchange(update.getMessage());
  }

  private List<? super PartialBotApiMethod<?>> sendBestExchange(Message message) {
    final String action = getPatternGroup(message.getText(), 1);
    final String currency = getPatternGroup(message.getText(), 2);
    final Map<String, String> bestPrivateBankExchangeRateFor = exchangeRateFacade
        .getBestPrivateBankExchangeRateFor(currency, action);

    if (isEmpty(bestPrivateBankExchangeRateFor)) {
      return singletonList(getSendMessageWithReplyKeyboardMarkup(message, NOT_AVAILABLE_MESSAGE,
          getCurrencyReplyKeyboardMarkup(action)));
    }

    final List<? super PartialBotApiMethod<?>> messages = new ArrayList<>();

    bestPrivateBankExchangeRateFor.forEach((bankName, msg) -> {
      messages.add(new SendMessage(message.getChatId().toString(), msg));

      final List<Location> locations = locationService.getLocationOf(bankName);
      locations.forEach(location -> messages.add(
          new SendLocation(message.getChatId().toString(), Double.valueOf(location.getLatitude()),
              Double.valueOf(location.getLongitude()))));
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

  @Override
  public String getCommand() {
    return BEST_EXCHANGE;
  }
}
