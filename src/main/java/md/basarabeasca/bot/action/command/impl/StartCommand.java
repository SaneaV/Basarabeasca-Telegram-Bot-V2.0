package md.basarabeasca.bot.action.command.impl;

import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
public class StartCommand implements Command {

    private static final String WELCOME_MESSAGE = "Добро пожаловать в Бессарабка бот V2.0. Воспользуйтесь клавишами " +
            "меню, чтобы узнать о новостях в нашем городе.";
    private static final String START_COMMAND = "/start";

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return singletonList(sendStartMessage(update.getMessage()));
    }

    @Override
    public String getCommand() {
        return START_COMMAND;
    }

    private SendMessage sendStartMessage(Message message) {
        return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                WELCOME_MESSAGE, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }
}
