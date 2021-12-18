package md.basarabeasca.bot.action.callback.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.callback.CallbackQueryHandler;
import md.basarabeasca.bot.action.callback.CallbackQueryType;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.FIND_NUMBER;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.PREVIOUS_PAGE;
import static md.basarabeasca.bot.action.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardForShowNumber;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageError;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithInlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class PreviousNumberPageCallBackQueryHandlerImpl implements CallbackQueryHandler {

    public final static String TO_MUCH_REQUESTS = "Слишком много запросов. Повторите попытку позже.";

    private final PhoneNumberServiceImpl phoneNumberService;
    private Integer lastDeletion;

    @Override
    public List<? super PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        List<PhoneNumberDto> phoneNumber = getNextPageNumbers(Long.valueOf(callbackQuery.getData().split(EMPTY_REGEX)[1]));
        long lastId = Long.parseLong(callbackQuery.getData().split(EMPTY_REGEX)[1]);

        if (phoneNumber.isEmpty()) {
            phoneNumber = getNextPageNumbers(lastId);
        }

        final StringBuilder formattedPhones = formatPhoneNumbers(phoneNumber, new StringBuilder());
        lastId = phoneNumber.get(phoneNumber.size() - 1).getId() + 1;

        try {
            if (!callbackQuery.getMessage().getMessageId().equals(lastDeletion)) {
                lastDeletion = callbackQuery.getMessage().getMessageId();

                final DeleteMessage deleteMessage = new DeleteMessage(chatId, callbackQuery.getMessage().getMessageId());
                final SendMessage sendMessage = getSendMessageWithInlineKeyboardMarkup(chatId, formattedPhones.toString(),
                        getSendInlineKeyboardForShowNumber(SEARCH_NUMBER, FIND_NUMBER.name(), lastId));

                return asList(deleteMessage, sendMessage);
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            return singletonList(getSendMessageError(callbackQuery.getMessage(), TO_MUCH_REQUESTS));
        }
    }

    private StringBuilder formatPhoneNumbers(List<PhoneNumberDto> phoneNumber, StringBuilder formattedPhones) {
        AtomicInteger i = new AtomicInteger();
        phoneNumber.forEach(
                number -> formattedPhones
                        .append(i.incrementAndGet())
                        .append(POINT)
                        .append(number.getPhoneNumber())
                        .append(HYPHEN)
                        .append(number.getDescription())
                        .append(NEW_LINE)
        );
        return formattedPhones;
    }

    private List<PhoneNumberDto> getNextPageNumbers(Long lastId) {
        return phoneNumberService.getPreviousPage(lastId);
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return PREVIOUS_PAGE;
    }
}
