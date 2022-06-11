package md.basarabeasca.bot.telegram.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getUsefulReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.AllArgsConstructor;
import md.basarabeasca.bot.infrastructure.service.TransportTimetableService;
import md.basarabeasca.bot.telegram.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class TimetablePublicTransportCommand implements Command {

  private static final String PUBLIC_TRANSPORT_TIMETABLE = "Расписание междугородних рейсов";

  private final TransportTimetableService transportTimetableService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return singletonList(sendTimetable(update.getMessage()));
  }

  private SendMessage sendTimetable(Message message) {
    return getSendMessageWithReplyKeyboardMarkup(message, transportTimetableService.getTimetable(),
        getUsefulReplyKeyboardMarkup());
  }

  @Override
  public String getCommand() {
    return PUBLIC_TRANSPORT_TIMETABLE;
  }
}
