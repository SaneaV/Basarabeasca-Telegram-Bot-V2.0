package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.service.api.TransportTimetableService;
import md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TransportTimetableCommand implements Command {

  private static final String PUBLIC_TRANSPORT_TIMETABLE = "Расписание междугородних рейсов";

  private final TransportTimetableService transportTimetableService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final SendMessage transportTimetable = getSendMessageWithReplyKeyboardMarkup(
        update.getMessage(), transportTimetableService.getTimetable(),
        ReplyKeyboardMarkupUtil.getUsefulReplyKeyboardMarkup());
    return singletonList(transportTimetable);
  }

  @Override
  public String getCommand() {
    return PUBLIC_TRANSPORT_TIMETABLE;
  }
}
