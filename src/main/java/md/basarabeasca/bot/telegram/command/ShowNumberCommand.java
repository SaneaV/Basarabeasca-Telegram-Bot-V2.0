package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardForShowNumber;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessage;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithInlineKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.facade.api.PhoneNumberFacade;
import md.basarabeasca.bot.telegram.callback.CallbackQueryType;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ShowNumberCommand implements Command {

  private static final String SEARCH_NUMBER = "Найти номер";
  private static final String SHOW_NUMBERS = "Полезные номера";
  private static final String PHONE_NUMBER_LIST_IS_EMPTY = "Список номеров пуст";

  private final PhoneNumberFacade phoneNumberFacade;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return singletonList(sendShowNumbers(update.getMessage()));
  }

  private BotApiMethod<?> sendShowNumbers(Message message) {
    final String phoneNumbers = phoneNumberFacade.getNextPage(0L);

    if (phoneNumbers.isEmpty()) {
      return getSendMessage(message, PHONE_NUMBER_LIST_IS_EMPTY);
    } else {
      final long lastId = phoneNumberFacade.getMaxIdOnPage(0L);
      return getSendMessageWithInlineKeyboardMarkup(message.getChatId().toString(), phoneNumbers,
          getSendInlineKeyboardForShowNumber(SEARCH_NUMBER, CallbackQueryType.FIND_NUMBER.name(), lastId));
    }
  }

  @Override
  public String getCommand() {
    return SHOW_NUMBERS;
  }
}
