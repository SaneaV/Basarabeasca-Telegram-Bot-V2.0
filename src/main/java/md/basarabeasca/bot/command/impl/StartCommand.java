package md.basarabeasca.bot.command.impl;

import md.basarabeasca.bot.command.ICommand;
import md.basarabeasca.bot.keyboard.KeyBoardUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements ICommand {

    @Override
    public SendMessage execute(Update update) {
        return sendStartMessage(update.getMessage());
    }

    private SendMessage sendStartMessage(final Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Добро пожаловать в Бессарабка бот V2.0. " +
                "Воспользуйтесь клавишами меню, чтобы узнать о новостях в нашем городе.");
        sendMessage.setReplyMarkup(KeyBoardUtil.getMainReplyKeyboardMarkup(message));
        return sendMessage;
    }
}
