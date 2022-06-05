package md.basarabeasca.bot.action.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getMoneyReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.facade.ExchangeRateFacade;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class BNMExchangeRatesCommand implements Command {

  private static final String EXCHANGE_RATES = "Курс валют BNM";

  private final ExchangeRateFacade exchangeRateFacade;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final String bnmExchangeRates = exchangeRateFacade.getBNMExchangeRates();
    final SendMessage message = getSendMessageWithReplyKeyboardMarkup(update.getMessage(),
        bnmExchangeRates, getMoneyReplyKeyboardMarkup());
    return singletonList(message);
  }

  @Override
  public String getCommand() {
    return EXCHANGE_RATES;
  }
}
