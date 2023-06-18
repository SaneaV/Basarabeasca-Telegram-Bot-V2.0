package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getBestFuelPriceReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.location.Location;
import md.basarabeasca.bot.facade.api.FuelFacade;
import md.basarabeasca.bot.infrastructure.service.api.LocationService;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class BestFuelPriceCommand implements Command {

  //Command
  private static final String BEST_FUEL_PRICE = "Лучшая цена на топливо - Бензин 95 - Дизель - Газ";
  private static final String BEST_FUEL_PRICE_COMMAND = "Лучшая цена на топливо";

  //Response
  private static final String MENU_RESPONSE = "Стоимость какого топлива вас интересует?";
  private static final String NOT_AVAILABLE_MESSAGE =
      "На данный момент ничего найти не можем. Возможные причины:\n"
          + "1. Все автозаправочные станции сейчас закрыты.\n"
          + "2. Топливо, которые вас интересует на данный момент не продаётся.";
  private static final String FINAL_MESSAGE = "*Цены получены с сайта: anre.md*";

  //Util
  //TODO: Create util class for Fuel flow
  private static final String DIESEL_RUS = "Дизель";
  private static final String PETROL_RUS = "Бензин 95";
  private static final String GAS_RUS = "Газ";
  private static final String DIESEL_ENG = "Diesel";
  private static final String PETROL_ENG = "Petrol";
  private static final String GAS_ENG = "Gas";
  private static final Map<String, String> FUEL_TYPE_TRANSLATION = new HashMap<>();

  static {
    FUEL_TYPE_TRANSLATION.put(DIESEL_RUS, DIESEL_ENG);
    FUEL_TYPE_TRANSLATION.put(PETROL_RUS, PETROL_ENG);
    FUEL_TYPE_TRANSLATION.put(GAS_RUS, GAS_ENG);
  }

  private final FuelFacade fuelFacade;
  private final LocationService locationService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return sendBestFuelPrice(update.getMessage());
  }

  private List<? super PartialBotApiMethod<?>> sendBestFuelPrice(Message message) {
    if (isBestFuelPriceMenuCommand(message.getText())) {
      return sendBestFuelPriceMenuMessage(message);
    }

    final String fuelType = FUEL_TYPE_TRANSLATION.get(message.getText());
    final Map<String, String> bestFuelPriceFor = fuelFacade.getBestFuelPriceFor(fuelType);

    if (isEmpty(bestFuelPriceFor)) {
      return singletonList(
          getSendMessageWithReplyKeyboardMarkup(message, NOT_AVAILABLE_MESSAGE, getBestFuelPriceReplyKeyboardMarkup()));
    }

    final List<? super PartialBotApiMethod<?>> messages = new ArrayList<>();

    bestFuelPriceFor.forEach((stationName, msg) -> {
      messages.add(new SendMessage(message.getChatId().toString(), msg));

      final List<Location> locations = locationService.getLocationOf(stationName);
      locations.forEach(location -> messages.add(
          new SendLocation(message.getChatId().toString(), Double.valueOf(location.getLatitude()),
              Double.valueOf(location.getLongitude()))));
    });

    messages.add(getSendMessageWithReplyKeyboardMarkup(message, FINAL_MESSAGE, getBestFuelPriceReplyKeyboardMarkup()));
    return messages;
  }

  private boolean isBestFuelPriceMenuCommand(String message) {
    return message.contains(BEST_FUEL_PRICE_COMMAND);
  }

  private List<? super PartialBotApiMethod<?>> sendBestFuelPriceMenuMessage(Message message) {
    final SendMessage response = getSendMessageWithReplyKeyboardMarkup(
        message, MENU_RESPONSE, getBestFuelPriceReplyKeyboardMarkup());
    return singletonList(response);
  }

  @Override
  public String getCommand() {
    return BEST_FUEL_PRICE;
  }
}
