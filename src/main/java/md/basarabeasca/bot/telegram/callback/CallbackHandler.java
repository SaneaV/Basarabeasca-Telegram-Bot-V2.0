package md.basarabeasca.bot.telegram.callback;

import static java.util.Arrays.asList;
import static md.basarabeasca.bot.telegram.callback.CallbackQueryType.FIND_NUMBER;
import static md.basarabeasca.bot.telegram.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardForShowNumber;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithInlineKeyboardMarkup;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackHandler {

  String SEARCH_NUMBER = "Найти номер";
  String TO_MUCH_REQUESTS = "Слишком много запросов. Повторите попытку позже.";

  List<? super PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery);

  CallbackQueryType getHandlerQueryType();

  default List<? super PartialBotApiMethod<?>> sendPhoneNumberMessage(Integer currentMessageId,
      String chatId, String phoneNumbers, long lastId) {
    final DeleteMessage deleteMessage = new DeleteMessage(chatId, currentMessageId);
    final SendMessage sendMessage = getSendMessageWithInlineKeyboardMarkup(chatId, phoneNumbers,
        getSendInlineKeyboardForShowNumber(SEARCH_NUMBER, FIND_NUMBER.name(), lastId));
    return asList(deleteMessage, sendMessage);
  }
}
