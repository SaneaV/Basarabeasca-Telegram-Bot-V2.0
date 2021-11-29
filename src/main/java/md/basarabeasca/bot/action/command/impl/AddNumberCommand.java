package md.basarabeasca.bot.action.command.impl;

import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
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
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessage;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
public class AddNumberCommand implements Command {

    private static final String REGEX = "(/addNumber)\\s(0\\d{8})\\s([A-Za-z0-9-А-Яа-я,.\\s]+)";
    private static final String ADD_NUMBER = "/addNumber";
    private static final String INCORRECT_NUMBER = "Номер некоректен";
    private static final String NO_ACCESS = "У вас нет подходящих прав для этой команды. Обратитесь к @SaneaV";

    private final PhoneNumberServiceImpl phoneNumberService;
    private final String adminId;

    @Autowired
    public AddNumberCommand(
            @Value("${telegrambot.adminId}") String adminId,
            PhoneNumberServiceImpl phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
        this.adminId = adminId;
    }

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        final String userId = update.getMessage().getFrom().getId().toString();

        if (userId.equals(adminId)) {
            return singletonList(sendAddNumber(update.getMessage()));
        } else {
            return singletonList(getSendMessage(update.getMessage().getChatId().toString(), NO_ACCESS));
        }
    }

    private BotApiMethod<?> sendAddNumber(Message message) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(message.getText());

        if (matcher.find()) {
            final String number = matcher.group(2);
            final String description = matcher.group(3);
            final String result = phoneNumberService.addNumber(number, description);
            return getSendMessage(message.getChatId().toString(), result);
        } else {
            return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                    INCORRECT_NUMBER, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
        }
    }

    @Override
    public String getCommand() {
        return ADD_NUMBER;
    }
}
