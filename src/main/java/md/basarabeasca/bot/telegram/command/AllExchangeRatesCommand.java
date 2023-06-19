package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.EXCHANGE_RATE_ONLY_IN_MAIN_OFFICE;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getMoneyReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.sendMessageWithReplyKeyboardMarkup;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.facade.api.ExchangeRateFacade;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AllExchangeRatesCommand implements Command {

  private static final String ALL_EXCHANGES = "Все курсы валют";
  private static final String NOT_AVAILABLE_MESSAGE = "Все банки в городе Басарабяска сейчас закрыты или ещё не обновили курсы валют на текущий день.";

  private final ExchangeRateFacade exchangeRateFacade;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return sendAllExchangeRates(update.getMessage());
  }

  private List<? super PartialBotApiMethod<?>> sendAllExchangeRates(Message message) {
    final List<String> allExchangeRates = exchangeRateFacade.getAllExchangeRates();

    if (isEmpty(allExchangeRates)) {
      return singletonList(sendMessageWithReplyKeyboardMarkup(message, NOT_AVAILABLE_MESSAGE, getMoneyReplyKeyboardMarkup()));
    }

    final List<? super PartialBotApiMethod<?>> messages = allExchangeRates.stream()
        .map(msg -> new SendMessage(message.getChatId().toString(), msg))
        .collect(Collectors.toList());

    messages.add(
        sendMessageWithReplyKeyboardMarkup(message, EXCHANGE_RATE_ONLY_IN_MAIN_OFFICE, getMoneyReplyKeyboardMarkup()));

    return messages;
  }

  @Override
  public String getCommand() {
    return ALL_EXCHANGES;
  }
}
