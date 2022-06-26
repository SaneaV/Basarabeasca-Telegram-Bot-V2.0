package md.basarabeasca.bot.infrastructure.parser;

import java.util.List;
import md.basarabeasca.bot.dao.domain.Fuel;

public interface FuelParser {

  List<Fuel> getANREFuelPrice();

  List<Fuel> getAllFuelPriceList();
}
