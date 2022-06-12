package md.basarabeasca.bot.telegram.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getCurrencyReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.telegram.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ExchangeActionCommand implements Command {

  private static final String BUY_SELL_CURRENCY = "Купить валюту/Продать валюту";
  private static final String RESPONSE = "Какую валюту вы хотите %s?";
  private static final String BUY = "Купить";
  private static final String SELL = "Продать";

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return singletonList(sendBestExchange(update.getMessage()));
  }

  private SendMessage sendBestExchange(Message message) {
    final String action = getAction(message.getText());
    return getSendMessageWithReplyKeyboardMarkup(message,
        String.format(RESPONSE, action.toLowerCase()),
        getCurrencyReplyKeyboardMarkup(action));
  }

  private String getAction(String action) {
    if (action.contains(BUY)) {
      return BUY;
    } else {
      return SELL;
    }
  }

  @Override
  public String getCommand() {
    return BUY_SELL_CURRENCY;
  }
}
