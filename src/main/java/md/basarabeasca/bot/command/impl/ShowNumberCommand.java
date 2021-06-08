package md.basarabeasca.bot.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.command.ICommand;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithInlineKeyboard;

@AllArgsConstructor
@Component
public class ShowNumberCommand implements ICommand {

    PhoneNumberServiceImpl phoneNumberService;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendShowNumbers(update.getMessage());
    }

    private SendMessage sendShowNumbers(final Message message) {

        List<PhoneNumberDto> phoneNumberDtos = phoneNumberService.getAllNumbers();
        StringBuilder stringBuilder = new StringBuilder();
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

        if (phoneNumberDtos.isEmpty()) {
            stringBuilder.append("Список номеров пуст");
        }

        return getSendMessageWithInlineKeyboard(message.getChatId().toString(),
                stringBuilder.toString(), ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }
}
