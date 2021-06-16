package md.basarabeasca.bot.action.callback.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.callback.CallbackQueryHandler;
import md.basarabeasca.bot.action.callback.CallbackQueryType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;

import static md.basarabeasca.bot.action.callback.CallbackQueryType.FIND_NUMBER;
import static md.basarabeasca.bot.settings.StringUtil.SEARCH_NUMBER_CALLBACK_DATA;

@AllArgsConstructor
@Component
public class FindNumberCallbackQueryHandlerImpl implements CallbackQueryHandler {
    private static final CallbackQueryType HANDLER_QUERY_TYPE = FIND_NUMBER;

    @Override
    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final Integer messageIdForReply = callbackQuery.getMessage().getMessageId();

        return SendMessage.builder()
                .text(SEARCH_NUMBER_CALLBACK_DATA)
                .chatId(chatId)
                .replyToMessageId(messageIdForReply)
                .replyMarkup(ForceReplyKeyboard.builder()
                        .forceReply(true)
                        .build())
                .build();
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return HANDLER_QUERY_TYPE;
    }
}
