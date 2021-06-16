package md.basarabeasca.bot.action.callback.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import md.basarabeasca.bot.action.callback.CallbackQueryHandler;
import md.basarabeasca.bot.action.callback.CallbackQueryType;
import md.basarabeasca.bot.bot.BasarabeascaBot;
import md.basarabeasca.bot.feature.hotnumbers.dao.model.PhoneNumber;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

import static md.basarabeasca.bot.action.callback.CallbackQueryType.FIND_NUMBER;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.PREVIOUS_PAGE;
import static md.basarabeasca.bot.settings.StringUtil.SEARCH_NUMBER;
import static md.basarabeasca.bot.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardForShowNumber;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageToMuchRequests;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageUnknown;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithInlineKeyboardMarkup;

@Component
@Lazy
@AllArgsConstructor
public class PreviousNumberPageCallBackQueryHandlerImpl implements CallbackQueryHandler {
    private static final CallbackQueryType HANDLER_QUERY_TYPE = PREVIOUS_PAGE;
    private final PhoneNumberServiceImpl phoneNumberService;
    private final BasarabeascaBot basarabeascaBot;

    @SneakyThrows
    @Override
    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        List<PhoneNumber> phoneNumber = getNextPageNumbers(Long.valueOf(callbackQuery.getData().split(" ")[1]));
        StringBuilder stringBuilder = new StringBuilder();
        Long lastId = Long.valueOf(callbackQuery.getData().split(" ")[1]);

        if (phoneNumber.isEmpty()) {
            phoneNumber = getNextPageNumbers(lastId);
        }

        int i = 0;
        for (PhoneNumber phone :
                phoneNumber) {
            stringBuilder
                    .append(++i)
                    .append(". ")
                    .append(phone.getNumber())
                    .append(" - ")
                    .append(phone.getDescription())
                    .append("\n");
        }
        lastId = phoneNumber.get(phoneNumber.size() - 1).getId() + 1;

        try {
            basarabeascaBot.execute(DeleteMessage.builder()
                    .chatId(chatId)
                    .messageId(callbackQuery.getMessage().getMessageId())
                    .build());
            return getSendMessageWithInlineKeyboardMarkup(chatId,
                    stringBuilder.toString(), getSendInlineKeyboardForShowNumber(SEARCH_NUMBER, FIND_NUMBER.name(), lastId));
        } catch (Exception exception) {
            return getSendMessageToMuchRequests(chatId);
        }
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return HANDLER_QUERY_TYPE;
    }

    private List<PhoneNumber> getNextPageNumbers(Long lastId) {
        return phoneNumberService.getPreviousPage(lastId);
    }
}
