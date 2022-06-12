package md.basarabeasca.bot.telegram.callback.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.callback.CallbackQueryType.FIND_NUMBER;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageToFindAPhoneNumber;

import java.util.List;
import md.basarabeasca.bot.telegram.callback.CallbackHandler;
import md.basarabeasca.bot.telegram.callback.CallbackQueryType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class FindNumberCallbackHandlerImpl implements CallbackHandler {

  public final static String SEARCH_NUMBER_CALLBACK_DATA = "Введите имя/организацию/заведение, чей номер вы ищите";

  @Override
  public List<? super PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery) {
    final String chatId = callbackQuery.getMessage().getChatId().toString();
    final Integer messageIdForReply = callbackQuery.getMessage().getMessageId();
    final SendMessage sendMessage = getSendMessageToFindAPhoneNumber(chatId,
        SEARCH_NUMBER_CALLBACK_DATA, messageIdForReply);

    return singletonList(sendMessage);
  }

  @Override
  public CallbackQueryType getHandlerQueryType() {
    return FIND_NUMBER;
  }
}
