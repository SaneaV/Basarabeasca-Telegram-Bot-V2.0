package md.basarabeasca.bot.action.command.impl;

import md.basarabeasca.bot.action.command.ICommand;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static md.basarabeasca.bot.settings.StringUtil.WELCOME_MESSAGE;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
@Lazy
public class StartCommand implements ICommand {

    @Override
    public SendMessage execute(Update update) {
        return sendStartMessage(update.getMessage());
    }

    private SendMessage sendStartMessage(final Message message) {
        return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                WELCOME_MESSAGE, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }
}
