package md.basarabeasca.bot.action.command.impl;

import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessage;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
public class DeleteNumberCommand implements Command {

    private static final String REGEX = "(/deleteNumber)\\s(0\\d{8})";
    private static final String DELETE_NUMBER = "/deleteNumber";
    private static final String INCORRECT_NUMBER = "Номер некоректен";
    private static final String NO_ACCESS = "У вас нет подходящих прав для этой команды. Обратитесь к @SaneaV";

    private final PhoneNumberServiceImpl phoneNumberService;
    private final String adminId;

    @Autowired
    public DeleteNumberCommand(
            @Value("${telegrambot.adminId}") String adminId,
            PhoneNumberServiceImpl phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
        this.adminId = adminId;
    }

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        final String userId = update.getMessage().getFrom().getId().toString();

        if (userId.equals(adminId)) {
            return singletonList(sendDeleteNumber(update.getMessage()));
        } else {
            return singletonList(getSendMessage(update.getMessage().getChatId().toString(), NO_ACCESS));
        }
    }

    private BotApiMethod<?> sendDeleteNumber(Message message) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(message.getText());

        if (matcher.find()) {
            final String number = matcher.group(2);
            final String result = phoneNumberService.deleteNumber(number);
            return getSendMessage(message.getChatId().toString(), result);
        } else {
            return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                    INCORRECT_NUMBER, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
        }
    }

    @Override
    public String getCommand() {
        return DELETE_NUMBER;
    }
}
