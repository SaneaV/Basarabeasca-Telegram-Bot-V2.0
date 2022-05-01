package md.basarabeasca.bot.action.command.impl;

import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getMoneyReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.service.TimetableBanksService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class TimetableBanksCommand implements Command {

  private static final String BANKS_TIMETABLE = "График работы банков";

  private final TimetableBanksService timetableBanksService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return Collections.singletonList(sendTimetable(update.getMessage()));
  }

  private SendMessage sendTimetable(Message message) {
    return getSendMessageWithReplyKeyboardMarkup(message, timetableBanksService.getTimetable(),
        getMoneyReplyKeyboardMarkup());
  }

  @Override
  public String getCommand() {
    return BANKS_TIMETABLE;
  }
}
