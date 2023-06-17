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
public class MainMenuCommand implements Command {

  private static final String BACK_TO_MAIN_MENU = "Вернуться в главное меню";
  private static final String WELCOME_MESSAGE = "Вы находитесь в главном меню бота. Воспользуйтесь клавишами ниже, чтобы узнать о новостях в нашем городе.";

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final SendMessage mainMenuMessage = getSendMessageWithReplyKeyboardMarkup(
        update.getMessage(), WELCOME_MESSAGE, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    return singletonList(mainMenuMessage);
  }

  @Override
  public String getCommand() {
    return BACK_TO_MAIN_MENU;
  }
}
