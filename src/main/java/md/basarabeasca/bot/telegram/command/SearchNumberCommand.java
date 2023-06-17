package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.facade.api.PhoneNumberFacade;
import md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class SearchNumberCommand implements Command {

  public final static String SEARCH_NUMBER = "Введите имя/организацию/заведение, чей номер вы ищите";

  private final PhoneNumberFacade phoneNumberFacade;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final Message message = update.getMessage();
    final String phoneNumbers = phoneNumberFacade.findByDescription(message.getText());
    final SendMessage phoneNumbersMessage = getSendMessageWithReplyKeyboardMarkup(message,
        phoneNumbers, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    return singletonList(phoneNumbersMessage);
  }

  @Override
  public String getCommand() {
    return SEARCH_NUMBER;
  }
}
