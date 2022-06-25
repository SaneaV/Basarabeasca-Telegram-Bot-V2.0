package md.basarabeasca.bot.telegram.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getMoneyReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.facade.ExchangeRateFacade;
import md.basarabeasca.bot.telegram.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AllExchangeRatesCommand implements Command {

  private static final String ALL_EXCHANGES = "Все курсы валют";
  private static final String NOT_AVAILABLE_MESSAGE =
      "Все банки в городе Басарабяска сейчас закрыты"
          + "или ещё не обновили курсы валют на текущий день.";
  private static final String EXCHANGE_RATE_ONLY_IN_MAIN_OFFICE = "*Курсы действительны только в "
      + "главном офисе банка и могут отличаться в его территориальных подразделениях.*";

  private final ExchangeRateFacade exchangeRateFacade;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return sendBestExchange(update.getMessage());
  }

  private List<? super PartialBotApiMethod<?>> sendBestExchange(Message message) {
    final List<String> allExchangeRates = exchangeRateFacade.getAllExchangeRates();

    if (isEmpty(allExchangeRates)) {
      return singletonList(getSendMessageWithReplyKeyboardMarkup(message, NOT_AVAILABLE_MESSAGE,
          getMoneyReplyKeyboardMarkup()));
    }

    final List<? super PartialBotApiMethod<?>> messages = allExchangeRates.stream()
        .map(msg -> new SendMessage(message.getChatId().toString(), msg))
        .collect(Collectors.toList());

    messages.add(getSendMessageWithReplyKeyboardMarkup(message, EXCHANGE_RATE_ONLY_IN_MAIN_OFFICE,
        getMoneyReplyKeyboardMarkup()));

    return messages;
  }

  @Override
  public String getCommand() {
    return ALL_EXCHANGES;
  }
}
