package md.basarabeasca.bot.action.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getMoneyReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.service.ExchangeRateService;
import md.basarabeasca.bot.service.UpdateDateService;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class BNMExchangeRatesCommand implements Command {

  private static final String ZONE_EUROPE_CHISINAU = "Europe/Chisinau";
  private static final String EXCHANGE_RATES = "Курс валют BNM";
  private static final String EXCHANGE_RATES_RESPONSE =
      "Курс валют Banca Nationala a Moldovei (%s):\n" +
          "\uD83C\uDDFA\uD83C\uDDF8 %s - %s MDL\n" +
          "\uD83C\uDDEA\uD83C\uDDFA %s - %s MDL\n" +
          "\uD83C\uDDFA\uD83C\uDDE6 %s - %s MDL\n" +
          "\uD83C\uDDF7\uD83C\uDDF4 %s - %s MDL\n" +
          "\uD83C\uDDF7\uD83C\uDDFA %s - %s MDL\n";

  private final UpdateDateService updateDateService;
  private final ExchangeRateService exchangeRateService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return singletonList(sendExchangeRates(update.getMessage()));
  }

  private SendMessage sendExchangeRates(Message message) {
    if (!updateDateService.getDate().isEqual(LocalDate.now(ZoneId.of(ZONE_EUROPE_CHISINAU)))) {
      updateDateService.updateDate();
      exchangeRateService.updateExchangeRates();
    }

    final List<ExchangeRateDto> exchangeRates = exchangeRateService.getBNMExchangeRates();
    final String response = String.format(EXCHANGE_RATES_RESPONSE,
        LocalDate.now(),
        exchangeRates.get(0).getCurrency(), exchangeRates.get(0).getPurchase(),
        exchangeRates.get(1).getCurrency(), exchangeRates.get(1).getPurchase(),
        exchangeRates.get(2).getCurrency(), exchangeRates.get(2).getPurchase(),
        exchangeRates.get(3).getCurrency(), exchangeRates.get(3).getPurchase(),
        exchangeRates.get(4).getCurrency(), exchangeRates.get(4).getPurchase());

    return getSendMessageWithReplyKeyboardMarkup(message, response, getMoneyReplyKeyboardMarkup());
  }

  @Override
  public String getCommand() {
    return EXCHANGE_RATES;
  }
}
