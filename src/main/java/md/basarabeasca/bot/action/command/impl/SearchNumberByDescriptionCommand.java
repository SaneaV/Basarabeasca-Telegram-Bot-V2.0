package md.basarabeasca.bot.action.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.command.ICommand;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

import static md.basarabeasca.bot.settings.StringUtil.PHONE_NUMBER_LIST_IS_EMPTY_OR_NUMBER_WAS_NOT_FOUND;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
@AllArgsConstructor
@Lazy
public class SearchNumberByDescriptionCommand implements ICommand {

    private final PhoneNumberServiceImpl phoneNumberService;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendSearchNumber(update.getMessage());
    }

    private SendMessage sendSearchNumber(final Message message) {
        List<PhoneNumberDto> phoneNumberDtos = phoneNumberService.findByDescription(message
                .getText());

        StringBuilder stringBuilder = new StringBuilder();
        if (phoneNumberDtos.isEmpty()) {
            stringBuilder.append(PHONE_NUMBER_LIST_IS_EMPTY_OR_NUMBER_WAS_NOT_FOUND);
        } else {
            int i = 0;
            for (PhoneNumberDto phone :
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

        return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                stringBuilder.toString(), ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }
}
