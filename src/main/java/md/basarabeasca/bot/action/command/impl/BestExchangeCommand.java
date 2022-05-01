package md.basarabeasca.bot.action.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getCurrencyActionReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
@AllArgsConstructor
public class BestExchangeCommand implements Command {

    private static final String BEST_EXCHANGE = "Лучший курс обмена";
    private static final String RESPONSE = "Что вас интересует?";

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return singletonList(sendBestExchange(update.getMessage()));
    }

    private SendMessage sendBestExchange(Message message) {
        return getSendMessageWithReplyKeyboardMarkup(message, RESPONSE, getCurrencyActionReplyKeyboardMarkup());
    }

    @Override
    public String getCommand() {
        return BEST_EXCHANGE;
    }
}
