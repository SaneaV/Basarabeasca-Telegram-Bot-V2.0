package md.basarabeasca.bot.action.command.impl;

import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getMoneyReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.service.BankHoursService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class BankHoursCommand implements Command {

  private static final String BANK_HOURS = "График работы банков";

  private final BankHoursService bankHoursService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return Collections.singletonList(sendBankHours(update.getMessage()));
  }

  private SendMessage sendBankHours(Message message) {
    return getSendMessageWithReplyKeyboardMarkup(message, bankHoursService.getBankHours(),
        getMoneyReplyKeyboardMarkup());
  }

  @Override
  public String getCommand() {
    return BANK_HOURS;
  }
}
