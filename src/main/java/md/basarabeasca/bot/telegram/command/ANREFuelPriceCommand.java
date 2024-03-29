package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getFuelReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.sendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.facade.api.FuelFacade;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ANREFuelPriceCommand implements Command {

  private static final String ANRE_FUEL_PRICE = "Цены на топливо НАРЭ";

  private final FuelFacade fuelFacade;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final String anreFuelPrice = fuelFacade.getANREFuelPrice();
    final SendMessage message = sendMessageWithReplyKeyboardMarkup(update.getMessage(), anreFuelPrice,
        getFuelReplyKeyboardMarkup());
    return singletonList(message);
  }

  @Override
  public String getCommand() {
    return ANRE_FUEL_PRICE;
  }
}
