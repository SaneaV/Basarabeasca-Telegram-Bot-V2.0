package md.basarabeasca.bot.action.command.impl;

import lombok.Getter;
import md.basarabeasca.bot.action.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Getter
@Component
public class MainMenuCommand implements Command {

    private static final String BACK_TO_MAIN_MENU = "Вернуться в главное меню";
    private static final String WELCOME_MESSAGE = "Вы находитесь в главном меню бота. Воспользуйтесь клавишами " +
            "ниже, чтобы узнать о новостях в нашем городе.";

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return singletonList(sendStartMessage(update.getMessage()));
    }

    @Override
    public String getCommand() {
        return BACK_TO_MAIN_MENU;
    }

    private SendMessage sendStartMessage(Message message) {
        return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                WELCOME_MESSAGE, getMainReplyKeyboardMarkup());
    }
}
