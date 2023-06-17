package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;

import md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UsefulCommand implements Command {

  private static final String USEFUL = "Полезная информация";
  private static final String USEFUL_MESSAGE = "Воспользуйтесь клавишами ниже, чтобы узнать полезную информацию о нашем городе";

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final SendMessage usefulMessage = getSendMessageWithReplyKeyboardMarkup(update.getMessage(),
        USEFUL_MESSAGE, ReplyKeyboardMarkupUtil.getUsefulReplyKeyboardMarkup());
    return singletonList(usefulMessage);
  }

  @Override
  public String getCommand() {
    return USEFUL;
  }
}
