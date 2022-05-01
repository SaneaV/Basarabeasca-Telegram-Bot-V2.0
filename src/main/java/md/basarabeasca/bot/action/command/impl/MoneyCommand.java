package md.basarabeasca.bot.action.command.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getMoneyReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class MoneyCommand implements Command {

    private static final String MONEY = "Деньги";
    private static final String RESPONSE = "Что вас интересует?";

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return singletonList(sendMoneyActionKeyBoard(update.getMessage()));
    }

    private SendMessage sendMoneyActionKeyBoard(Message message) {
        return getSendMessageWithReplyKeyboardMarkup(message, RESPONSE, getMoneyReplyKeyboardMarkup());
    }

    @Override
    public String getCommand() {
        return MONEY;
    }
}
