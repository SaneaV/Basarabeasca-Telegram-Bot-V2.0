package md.basarabeasca.bot.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.command.ICommand;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import md.basarabeasca.bot.keyboard.KeyBoardUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
@AllArgsConstructor
public class DeleteNumberCommand implements ICommand {

    PhoneNumberServiceImpl phoneNumberService;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendDeleteNumber(update.getMessage());
    }

    private SendMessage sendDeleteNumber(final Message message) {

        String[] numberArray = message.getText().split(" ");

        String number = numberArray[1];
        if (!number.matches("0\\d{8}")) {
            return SendMessage.builder()
                    .chatId(message.getChatId().toString())
                    .text("Номер некоректен")
                    .replyMarkup(KeyBoardUtil.getMainReplyKeyboardMarkup())
                    .build();
        }

        String result = phoneNumberService.deleteNumber(number);

        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(result)
                .replyMarkup(KeyBoardUtil.getMainReplyKeyboardMarkup())
                .build();
    }
}
