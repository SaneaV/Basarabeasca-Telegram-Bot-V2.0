package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;

import java.util.List;

import md.basarabeasca.bot.telegram.command.api.Command;
import md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil;
import md.basarabeasca.bot.telegram.util.message.MessageUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class FuelCommand implements Command {

  private static final String FUEL = "Топливо";
  private static final String RESPONSE = "Что вас интересует?";

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final SendMessage moneyMessage = MessageUtil.getSendMessageWithReplyKeyboardMarkup(
        update.getMessage(), RESPONSE, ReplyKeyboardMarkupUtil.getFuelReplyKeyboardMarkup());
    return singletonList(moneyMessage);
  }

  @Override
  public String getCommand() {
    return FUEL;
  }
}
