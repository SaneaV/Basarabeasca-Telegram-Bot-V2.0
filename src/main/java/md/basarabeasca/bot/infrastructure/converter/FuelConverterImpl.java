package md.basarabeasca.bot.infrastructure.converter;

import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import md.basarabeasca.bot.domain.fuel.Fuel;
import md.basarabeasca.bot.infrastructure.converter.api.FuelConverter;
import org.springframework.stereotype.Component;

@Component
public class FuelConverterImpl implements FuelConverter {

  private static final String DIESEL_RUS = "Дизель";
  private static final String PETROL_RUS = "Бензин 95";
  private static final String GAS_RUS = "Газ";
  private static final String DIESEL_ENG = "Diesel";
  private static final String PETROL_ENG = "Petrol";
  private static final String GAS_ENG = "Gas";

  private static final Map<String, String> FUEL_TYPE_TRANSLATION = new HashMap<>();

  private static final String MESSAGE = "\uD83D\uDCB5 Цены НАРЭ на топливо (%s):\n"
      + "\uD83D\uDFE2 %s - %s лей\n"
      + "\uD83D\uDFE2 %s - %s лей";

  private static final String FULL_PRICE_START_MESSAGE = "⛽ Автозаправка %s (%s):\n";
  private static final String FULL_PRICE_MESSAGE = "\uD83D\uDFE2 %s - %s лей\n";
  private static final String NOT_AVAILABLE_STATION = "На данный момент заправка %s недоступна для проверки цены";
  private static final String BEST_FUEL_PRICE_MESSAGE =
      "Лучше всего сегодня (%s) можно купить %s в городе Басарабяска на автозаправке:\n⛽%s - %s лей";

  static {
    FUEL_TYPE_TRANSLATION.put(DIESEL_ENG, DIESEL_RUS);
    FUEL_TYPE_TRANSLATION.put(PETROL_ENG, PETROL_RUS);
    FUEL_TYPE_TRANSLATION.put(GAS_ENG, GAS_RUS);
  }

  @Override
  public String toMessage(List<Fuel> fuelList) {
    final Fuel fuel_one = fuelList.get(0);
    final Fuel fuel_two = fuelList.get(1);
    return String.format(MESSAGE, LocalDate.now(), FUEL_TYPE_TRANSLATION.get(fuel_one.getType()),
        fuel_one.getPrice(), FUEL_TYPE_TRANSLATION.get(fuel_two.getType()), fuel_two.getPrice());
  }

  @Override
  public List<String> toMessage(Map<String, List<Fuel>> fuelList) {
    final List<String> messages = new ArrayList<>();
    fuelList.forEach((k, v) -> messages.add(populateFuelPriceMessage(k, v)));

    return messages;
  }

  @Override
  public String toMessage(Fuel fuel) {
    return String.format(BEST_FUEL_PRICE_MESSAGE, LocalDate.now(),
        FUEL_TYPE_TRANSLATION.get(fuel.getType()), fuel.getStation(), fuel.getPrice());
  }


  private String populateFuelPriceMessage(String stationName, List<Fuel> fuelList) {
    if (isEmpty(fuelList)) {
      return String.format(NOT_AVAILABLE_STATION, stationName);
    }

    final StringBuilder message = new StringBuilder(
        String.format(FULL_PRICE_START_MESSAGE, stationName, LocalDate.now()));

    fuelList.stream()
        .filter(f -> nonNull(f.getPrice()))
        .forEach(f -> message.append(
            String.format(FULL_PRICE_MESSAGE, FUEL_TYPE_TRANSLATION.get(f.getType()),
                f.getPrice()))
        );

    return message.toString();
  }
}
