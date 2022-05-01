package md.basarabeasca.bot.action.callback.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.FIND_NUMBER;

import java.util.List;
import lombok.Getter;
import md.basarabeasca.bot.action.callback.CallbackQueryHandler;
import md.basarabeasca.bot.action.callback.CallbackQueryType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;

@Getter
@Component
public class FindNumberCallbackQueryHandlerImpl implements CallbackQueryHandler {

  public final static String SEARCH_NUMBER_CALLBACK_DATA = "Введите имя/организацию/заведение, чей номер вы ищите";

  @Override
  public List<? super PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery) {
    final String chatId = callbackQuery.getMessage().getChatId().toString();
    final Integer messageIdForReply = callbackQuery.getMessage().getMessageId();

    final SendMessage sendMessage = SendMessage.builder()
        .text(SEARCH_NUMBER_CALLBACK_DATA)
        .chatId(chatId)
        .replyToMessageId(messageIdForReply)
        .replyMarkup(ForceReplyKeyboard.builder()
            .forceReply(true)
            .build())
        .build();

    return singletonList(sendMessage);
  }

  @Override
  public CallbackQueryType getHandlerQueryType() {
    return FIND_NUMBER;
  }
}
