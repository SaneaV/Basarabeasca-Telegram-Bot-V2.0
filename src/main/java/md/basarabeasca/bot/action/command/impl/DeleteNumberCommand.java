package md.basarabeasca.bot.action.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.command.ICommand;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

import static md.basarabeasca.bot.settings.Command.DELETE_NUMBER;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessage;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
@AllArgsConstructor
@Lazy
public class DeleteNumberCommand implements ICommand {

    PhoneNumberServiceImpl phoneNumberService;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendDeleteNumber(update.getMessage());
    }

    @Override
    public String getCommand() {
        return DELETE_NUMBER;
    }

    private SendMessage sendDeleteNumber(final Message message) {

        String[] numberArray = message.getText().split(" ");

        String number = numberArray[1];
        if (!number.matches("0\\d{8}")) {
            return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                    "Номер некоректен", ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
        }

        String result = phoneNumberService.deleteNumber(number);

        return getSendMessage(message.getChatId().toString(), result);
    }
}
