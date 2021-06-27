package md.basarabeasca.bot.action.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.command.ICommand;
import md.basarabeasca.bot.feature.hotnumbers.dao.model.PhoneNumber;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

import static md.basarabeasca.bot.action.callback.CallbackQueryType.FIND_NUMBER;
import static md.basarabeasca.bot.settings.Command.SHOW_NUMBERS;
import static md.basarabeasca.bot.settings.StringUtil.PHONE_NUMBER_LIST_IS_EMPTY;
import static md.basarabeasca.bot.settings.StringUtil.SEARCH_NUMBER;
import static md.basarabeasca.bot.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardForShowNumber;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithInlineKeyboardMarkup;

@AllArgsConstructor
@Component
@Lazy
public class ShowNumberCommand implements ICommand {

    private final PhoneNumberServiceImpl phoneNumberService;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendShowNumbers(update.getMessage());
    }

    @Override
    public String getCommand() {
        return SHOW_NUMBERS;
    }

    private SendMessage sendShowNumbers(final Message message) {

        List<PhoneNumber> phoneNumberDtos = phoneNumberService.getNextPage(0L);
        Long lastId = phoneNumberDtos.get(phoneNumberDtos.size() - 1).getId() + 1;
        StringBuilder stringBuilder = new StringBuilder();

        if (phoneNumberDtos.isEmpty()) {
            stringBuilder.append(PHONE_NUMBER_LIST_IS_EMPTY);
        } else {
            int i = 0;
            for (PhoneNumber phone :
                    phoneNumberDtos) {
                stringBuilder
                        .append(++i)
                        .append(". ")
                        .append(phone.getNumber())
                        .append(" - ")
                        .append(phone.getDescription())
                        .append("\n");
            }
        }

        return getSendMessageWithInlineKeyboardMarkup(message.getChatId().toString(),
                stringBuilder.toString(), getSendInlineKeyboardForShowNumber(SEARCH_NUMBER, FIND_NUMBER.name(), lastId));
    }
}
