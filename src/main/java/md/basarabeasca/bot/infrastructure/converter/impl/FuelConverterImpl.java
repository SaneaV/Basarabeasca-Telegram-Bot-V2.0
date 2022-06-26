package md.basarabeasca.bot.infrastructure.converter.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import md.basarabeasca.bot.dao.domain.Fuel;
import md.basarabeasca.bot.infrastructure.converter.FuelConverter;
import org.springframework.stereotype.Component;

@Component
public class FuelConverterImpl implements FuelConverter {

  private static final String DIESEL_RUS = "Дизель";
  private static final String PETROL_RUS = "Бензин 95";
  private static final String DIESEL_ENG = "Diesel";
  private static final String PETROL_ENG = "Petrol";

  private static final Map<String, String> FUEL_TYPE_TRANSLATION = Map.of(DIESEL_ENG, DIESEL_RUS,
      PETROL_ENG, PETROL_RUS);

  private static final String MESSAGE = "Цены НАРЭ на топливо (%s)\n"
      + "%s - %s лей\n"
      + "%s - %s лей";

  @Override
  public String toMessage(List<Fuel> fuelList) {
    final Fuel fuel_one = fuelList.get(0);
    final Fuel fuel_two = fuelList.get(1);
    return String.format(MESSAGE, LocalDate.now(), FUEL_TYPE_TRANSLATION.get(fuel_one.getType()),
        fuel_one.getPrice(), FUEL_TYPE_TRANSLATION.get(fuel_two.getType()), fuel_two.getPrice());
  }
}
