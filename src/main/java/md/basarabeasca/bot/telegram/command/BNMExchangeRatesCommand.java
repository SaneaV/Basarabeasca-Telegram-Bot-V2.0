package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.facade.api.ExchangeRateFacade;
import md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class BNMExchangeRatesCommand implements Command {

  private static final String BNM_EXCHANGE_RATES = "Курс валют BNM";

  private final ExchangeRateFacade exchangeRateFacade;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final String bnmExchangeRates = exchangeRateFacade.getBNMExchangeRates();
    final SendMessage message = getSendMessageWithReplyKeyboardMarkup(update.getMessage(),
        bnmExchangeRates, ReplyKeyboardMarkupUtil.getMoneyReplyKeyboardMarkup());
    return singletonList(message);
  }

  @Override
  public String getCommand() {
    return BNM_EXCHANGE_RATES;
  }
}
