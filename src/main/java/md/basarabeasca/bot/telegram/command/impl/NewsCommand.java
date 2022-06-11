package md.basarabeasca.bot.telegram.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getNewsReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.Getter;
import md.basarabeasca.bot.telegram.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Component
public class NewsCommand implements Command {

  private static final String WELCOME_MESSAGE =
      "Воспользуйтесь клавишами ниже, чтобы получить новости с нужного " +
          "вам сайта.";
  private static final String CITY_NEWS = "Новости города";

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return singletonList(sendStartMessage(update.getMessage()));
  }

  @Override
  public String getCommand() {
    return CITY_NEWS;
  }

  private SendMessage sendStartMessage(Message message) {
    return getSendMessageWithReplyKeyboardMarkup(message, WELCOME_MESSAGE,
        getNewsReplyKeyboardMarkup());
  }
}
