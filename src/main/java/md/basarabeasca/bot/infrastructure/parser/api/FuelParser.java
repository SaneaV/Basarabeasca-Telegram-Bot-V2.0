package md.basarabeasca.bot.infrastructure.parser.api;

import java.util.List;
import md.basarabeasca.bot.domain.fuel.Fuel;

public interface FuelParser {

  List<Fuel> getANREFuelPrice();

  List<Fuel> getAllFuelPriceList();
}
