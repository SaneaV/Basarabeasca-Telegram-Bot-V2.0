package md.basarabeasca.bot.action.command.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.parser.TimetableTransportParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getUsefulReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class TimetablePublicTransportCommand implements Command {

    private static final String PUBLIC_TRANSPORT_TIMETABLE = "Расписание междугородних рейсов";

    private final TimetableTransportParser timetableTransportParser;

    @SneakyThrows
    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return singletonList(sendTimetable(update.getMessage()));
    }

    private SendMessage sendTimetable(Message message) throws IOException {
        return getSendMessageWithReplyKeyboardMarkup(message, timetableTransportParser.getTimetable(),
                getUsefulReplyKeyboardMarkup());
    }

    @Override
    public String getCommand() {
        return PUBLIC_TRANSPORT_TIMETABLE;
    }
}
