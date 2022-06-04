package md.basarabeasca.bot.action.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getUsefulReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.Getter;
import md.basarabeasca.bot.action.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Component
public class UsefulCommand implements Command {

  private static final String USEFUL = "Полезная информация";
  private static final String USEFUL_MESSAGE =
      "Воспользуйтесь клавишами ниже, чтобы узнать полезную информацию о " +
          "нашем городе";

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return singletonList(sendStartMessage(update.getMessage()));
  }

  @Override
  public String getCommand() {
    return USEFUL;
  }

  private SendMessage sendStartMessage(Message message) {
    return getSendMessageWithReplyKeyboardMarkup(message, USEFUL_MESSAGE,
        getUsefulReplyKeyboardMarkup());
  }
}
