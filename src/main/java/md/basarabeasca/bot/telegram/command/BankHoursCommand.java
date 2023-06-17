package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.service.api.BankHoursService;
import md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class BankHoursCommand implements Command {

  private static final String BANK_HOURS = "График работы банков";

  private final BankHoursService bankHoursService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final SendMessage bankHours = getSendMessageWithReplyKeyboardMarkup(update.getMessage(),
        bankHoursService.getBankHours(), ReplyKeyboardMarkupUtil.getMoneyReplyKeyboardMarkup());
    return singletonList(bankHours);
  }

  @Override
  public String getCommand() {
    return BANK_HOURS;
  }
}
