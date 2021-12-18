package md.basarabeasca.bot.action.command.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.FIND_NUMBER;
import static md.basarabeasca.bot.action.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardForShowNumber;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessage;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithInlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class ShowNumberCommand implements Command {

    private static final String SEARCH_NUMBER = "Найти номер";
    private static final String SHOW_NUMBERS = "Полезные номера";
    private static final String PHONE_NUMBER_LIST_IS_EMPTY = "Список номеров пуст";
    private static final String POINT = ". ";
    private static final String HYPHEN = " - ";
    private static final String NEW_LINE = "\n";

    private final PhoneNumberServiceImpl phoneNumberService;

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return singletonList(sendShowNumbers(update.getMessage()));
    }

    private BotApiMethod<?> sendShowNumbers(Message message) {
        final List<PhoneNumberDto> phoneNumberDtos = phoneNumberService.getNextPage(0L);
        final StringBuilder stringBuilder = new StringBuilder();

        if (phoneNumberDtos.isEmpty()) {
            stringBuilder.append(PHONE_NUMBER_LIST_IS_EMPTY);
            return getSendMessage(message, stringBuilder.toString());
        } else {
            final long lastId = phoneNumberDtos.get(phoneNumberDtos.size() - 1).getId() + 1;
            int i = 0;
            for (PhoneNumberDto number :
                    phoneNumberDtos) {
                stringBuilder
                        .append(++i)
                        .append(POINT)
                        .append(number.getPhoneNumber())
                        .append(HYPHEN)
                        .append(number.getDescription())
                        .append(NEW_LINE);
            }

            return getSendMessageWithInlineKeyboardMarkup(message.getChatId().toString(), stringBuilder.toString(),
                    getSendInlineKeyboardForShowNumber(SEARCH_NUMBER, FIND_NUMBER.name(), lastId));
        }
    }

    @Override
    public String getCommand() {
        return SHOW_NUMBERS;
    }
}
